package org.mindera.system.controller;

import com.mongodb.client.MongoClient;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mindera.dto.hotel.CreateHotelDto;
import org.mindera.dto.hotel.CreateRoomDto;
import org.mindera.dto.reservations.CreateReservationMultiDto;
import org.mindera.dto.reservations.CreateRoomsReservationDto;
import org.mindera.model.hotel.Facilities;
import org.mindera.model.hotel.RoomType;
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
    private final String HOTEL_ENDPOINT = "api/v1/hotel";

    @Inject
    MongoClient mongoClient;
    @Inject
    HotelService hotelService;
    @Inject
    ReservationService reservationService;


    @AfterEach
    public void setUp() {
        mongoClient.getDatabase("hoteldb").getCollection("reservations").drop();
        mongoClient.getDatabase("hoteldb").getCollection("Hotels").drop();
    }

    public CreateHotelDto createHoteldemo() {
        return new CreateHotelDto("HotelTest", "Sample Address", "912345671",
                Set.of(new CreateRoomDto(101, 1, RoomType.SINGLEROOM, 100)),
                Set.of(Facilities.BAR));
    }

    public CreateHotelDto createHoteldemo2() throws HotelDuplicationException {
        return new CreateHotelDto("HotelTest2", "Sample Address2", "912345672",
                Set.of(new CreateRoomDto(101, 1, RoomType.SINGLEROOM, 100)),
                Set.of(Facilities.BAR));
    }

    public CreateReservationMultiDto createReservationDemo() {
        CreateRoomsReservationDto createRoomsReservationDto = new CreateRoomsReservationDto(RoomType.SINGLEROOM);
        return new CreateReservationMultiDto(LocalDate.parse("2024-05-05"), LocalDate.parse("2024-05-10"), "HotelTest", "JJ", "919191919", "2999999999", List.of(createRoomsReservationDto));
    }

    @Test
    public void testCreateReservation() throws HotelDuplicationException, ReservationExistsException, RoomExistsException, HotelExistsException {
        CreateHotelDto hotel = createHoteldemo();
        hotelService.addHotel(hotel);
        CreateReservationMultiDto reservation = createReservationDemo();


        given()
                .body(reservation)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().post(HOTEL_ENDPOINT)
                .then()
                .statusCode(201);
    }


}



