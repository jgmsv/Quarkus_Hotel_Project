package org.mindera.system.controller;

import com.mongodb.client.MongoClient;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import org.apache.http.HttpHeaders;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindera.dto.hotel.CreateHotelDto;
import org.mindera.dto.hotel.CreateRoomDto;
import org.mindera.dto.reservations.CreateReservationArrivaDeparturelDto;
import org.mindera.dto.reservations.CreateReservationMultiDto;
import org.mindera.dto.reservations.CreateRoomsReservationDto;
import org.mindera.model.hotel.Facilities;
import org.mindera.model.hotel.RoomType;
import org.mindera.model.reservations.ReservationsMulti;
import org.mindera.service.hotel.HotelService;
import org.mindera.service.reservations.ReservationService;
import org.mindera.util.exceptions.hotel.HotelDuplicationException;
import org.mindera.util.exceptions.hotel.HotelExistsException;
import org.mindera.util.exceptions.reservations.ReservationExistsException;
import org.mindera.util.exceptions.room.RoomExistsException;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class ReservationControllerTest {
    private final String RESERVAITON_ENDPOINT = "api/v1/reservations";
    private final String reservationDemo = "{" +
            "\"arrival\": \"2024-05-05\"," +
            "\"departure\": \"2024-05-10\"," +
            "\"hotelN\": \"HotelTest\"," +
            "\"fullName\": \"JJ\"," +
            "\"phoneNumber\": \"919191919\"," +
            "\"vat\": \"2999999999\"," +
            "\"roomReservations\": [" +
            "{" +
            "\"roomType\": \"SINGLEROOM\"" +
            "}" +
            "]" +
            "}";
    private final String reservationBug
            = "{" +
            "\"arrival\": \"2024-05-05\"," +
            "\"departure\": \"2024-05-10\"," +
            "\"hotelN\": \"HotelTest\"," +
            "\"fullName\": \"JJ\"," +
            "\"phoneNumber\": \"919191919\"," +
            "\"vat\": \"2999999999\"," +
            "\"roomReservations\": [" +
            "{" +
            "\"roomType\": \"SINGLEROOM\"" +
            "}" +
            "]" +
            "}";
    @Inject
    MongoClient mongoClient;
    @Inject
    HotelService hotelService;
    @Inject
    ReservationService reservationService;
    CreateRoomsReservationDto createRoomsReservationDto = new CreateRoomsReservationDto(RoomType.SINGLEROOM);

    @AfterEach
    public void setUp() {
        mongoClient.getDatabase("reservationmultidb").getCollection("Reservations").drop();
    }

    @BeforeEach
    public void setUpHotel() {
        mongoClient.getDatabase("hoteldb").getCollection("Hotels").drop();
    }


    @Test
    public void testCreateReservation() throws HotelDuplicationException {
        hotelService.addHotel(createHoteldemo());
        given()
                .body(reservationDemo)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().post(RESERVAITON_ENDPOINT + "/HotelTest")
                .then()
                .statusCode(201);

    }

    @Test
    public void testCreateduplicationReservation() throws ReservationExistsException, RoomExistsException, HotelExistsException, HotelDuplicationException {
        hotelService.addHotel(createHoteldemo());
        CreateReservationMultiDto reservationDemo3 = createReservationDemo();
        reservationService.addReservation(reservationDemo3);
        CreateReservationMultiDto reservationDemo4 = createReservationRoomDuplication();

        given()
                .body(reservationDemo4)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().post(RESERVAITON_ENDPOINT + "/HotelTest")
                .then()
                .statusCode(400);
    }

    @Test
    public void testCreateReservationWrongRoom() throws ReservationExistsException, RoomExistsException, HotelExistsException, HotelDuplicationException {
        hotelService.addHotel(createHoteldemo());
        CreateReservationMultiDto reservationDemo4 = createReservationDemo();
        reservationService.addReservation(reservationDemo4);


        given()
                .body(reservationDemo4)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().post(RESERVAITON_ENDPOINT + "/HotelTest")
                .then()
                .statusCode(400);
    }

    @Test
    public void testCreateReservationWrongHotel() throws ReservationExistsException, RoomExistsException, HotelExistsException, HotelDuplicationException {
        hotelService.addHotel(createHoteldemo());
        CreateReservationMultiDto reservationDemo = createReservationHotelW();
        reservationService.addReservation(reservationDemo);


        given()
                .body(reservationDemo)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().post(RESERVAITON_ENDPOINT + "/HotelTest2")
                .then()
                .statusCode(400);
    }

    @Test
    public void testUpdateReservation() throws ReservationExistsException, RoomExistsException, HotelExistsException, HotelDuplicationException {
        hotelService.addHotel(createHoteldemo());
        CreateReservationMultiDto createReservation = createReservationDemo();
        ReservationsMulti reservationsMulti = reservationService.addReservation(createReservation);
        ObjectId id = reservationsMulti.id;

        given()
                .body(reservationDemo)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().put(RESERVAITON_ENDPOINT + "/update/" + id)
                .then()
                .statusCode(200);
    }

    @Test
    public void testUpdateInvalidDateReservation() throws ReservationExistsException, RoomExistsException, HotelExistsException, HotelDuplicationException {
        hotelService.addHotel(createHoteldemo());
        CreateReservationMultiDto createReservation = createReservationDemo();
        ReservationsMulti reservationsMulti = reservationService.addReservation(createReservation);
        ObjectId id = reservationsMulti.id;
        CreateReservationArrivaDeparturelDto dates = new CreateReservationArrivaDeparturelDto(LocalDate.parse("2024-05-05"), LocalDate.parse("2024-05-01"));

        given()
                .body(dates)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().put(RESERVAITON_ENDPOINT + "/update/" + id)
                .then()
                .statusCode(400);
    }

    @Test
    public void testUpdateInvalidDates() throws ReservationExistsException, RoomExistsException, HotelExistsException, HotelDuplicationException {
        hotelService.addHotel(createHoteldemo());
        CreateReservationMultiDto createReservation = createReservationDemo();
        ReservationsMulti reservationsMulti = reservationService.addReservation(createReservation);
        ObjectId id = reservationsMulti.id;
        CreateReservationArrivaDeparturelDto dates = new CreateReservationArrivaDeparturelDto(LocalDate.parse("2021-01-05"), LocalDate.parse("2024-05-01"));

        given()
                .body(dates)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().put(RESERVAITON_ENDPOINT + "/update/" + id)
                .then()
                .statusCode(400);
    }

    @Test
    public void testFindAllReservations() throws ReservationExistsException, RoomExistsException, HotelExistsException, HotelDuplicationException {
        hotelService.addHotel(createHoteldemo());
        CreateReservationMultiDto createReservation = createReservationDemo();
        reservationService.addReservation(createReservation);

        given()
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().get(RESERVAITON_ENDPOINT + "?page=1")
                .then()
                .statusCode(200);
    }

    @Test
    public void testFindReservationById() throws ReservationExistsException, RoomExistsException, HotelExistsException, HotelDuplicationException {
        hotelService.addHotel(createHoteldemo());
        CreateReservationMultiDto createReservation = createReservationDemo();
        ReservationsMulti reservationsMulti = reservationService.addReservation(createReservation);
        ObjectId id = reservationsMulti.id;

        given()
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().get(RESERVAITON_ENDPOINT + "/" + id)
                .then()
                .statusCode(200);
    }

    public CreateReservationMultiDto createReservationDemo() {
        CreateRoomsReservationDto createRoomsReservationDto = new CreateRoomsReservationDto(RoomType.SINGLEROOM);
        return new CreateReservationMultiDto(
                LocalDate.parse("2024-05-05"),
                LocalDate.parse("2024-05-10"),
                "HotelTest",
                "JJ",
                "919191919",
                "2999999999",
                List.of(createRoomsReservationDto)
        );
    }

    public CreateReservationMultiDto createReservationRoomDuplication() {
        CreateRoomsReservationDto createRoomsReservationDto = new CreateRoomsReservationDto(RoomType.SINGLEROOM);
        return new CreateReservationMultiDto(
                LocalDate.parse("2024-05-05"),
                LocalDate.parse("2024-05-10"),
                "HotelTest",
                "JJ2",
                "919191912",
                "2999999992",
                List.of(createRoomsReservationDto)
        );
    }

    public CreateReservationMultiDto createReservationRoomW() {
        CreateRoomsReservationDto createRoomsReservationDto = new CreateRoomsReservationDto(RoomType.valueOf("WRONGROOM"));
        return new CreateReservationMultiDto(
                LocalDate.parse("2024-05-05"),
                LocalDate.parse("2024-05-10"),
                "HotelTest",
                "JJ2",
                "919191912",
                "2999999992",
                List.of(createRoomsReservationDto)
        );
    }

    public CreateReservationMultiDto createReservationHotelW() {
        CreateRoomsReservationDto createRoomsReservationDto = new CreateRoomsReservationDto(RoomType.SINGLEROOM);
        return new CreateReservationMultiDto(
                LocalDate.parse("2024-05-05"),
                LocalDate.parse("2024-05-10"),
                "HotelTest",
                "JJ2",
                "919191912",
                "2999999992",
                List.of(createRoomsReservationDto)
        );
    }

    public CreateHotelDto createHoteldemo() {
        return new CreateHotelDto("HotelTest", "Sample Address", "912345671",
                Set.of(new CreateRoomDto(101, 1, RoomType.SINGLEROOM, 100)),
                Set.of(Facilities.BAR));
    }


}



