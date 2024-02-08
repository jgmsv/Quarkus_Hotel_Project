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
import org.mindera.model.hotel.Facilities;
import org.mindera.model.hotel.Hotel;
import org.mindera.model.hotel.RoomType;
import org.mindera.model.hotel.Rooms;
import org.mindera.service.hotel.HotelService;
import org.mindera.util.exceptions.hotel.HotelDuplicationException;

import java.util.HashSet;
import java.util.Set;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class HotelControllerTest {

    private final String HOTEL_ENDPOINT = "api/v1/hotel";

    @Inject
    MongoClient mongoClient;
    @Inject
    HotelService hotelService;

    @AfterEach
    public void setUp() {
        mongoClient.getDatabase("hoteldb").getCollection("Hotels").drop();
    }

    private Set<Rooms> createRoomsSet() {
        Set<Rooms> rooms = new HashSet<>();
        rooms.add(Rooms.builder()
                .roomNumber(101)
                .numberOfBeds(1)
                .roomType(RoomType.SINGLEROOM)
                .roomPrice(100)
                .build());
        return rooms;
    }

    private Set<Facilities> createFacilitiesSet() {
        Set<Facilities> facilities = new HashSet<>();
        facilities.add(Facilities.BAR);
        return facilities;
    }

    public CreateHotelDto createHoteldemo() {
        return new CreateHotelDto("HotelTest", "Sample Address", "912345671",
                Set.of(new CreateRoomDto(101, 1, RoomType.SINGLEROOM, 100)),
                Set.of(Facilities.BAR));
    }

    public CreateHotelDto createHoteldemo2() {
        return new CreateHotelDto("HotelTest2", "Sample Address2", "912345672",
                Set.of(new CreateRoomDto(101, 1, RoomType.SINGLEROOM, 100)),
                Set.of(Facilities.BAR));
    }

    @Test
    public void testGetAllHotel() {
        given()
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().get(HOTEL_ENDPOINT + "?page=1")
                .then()
                .statusCode(200)
                .body("size()", is(0));
    }

    @Test
    void testAddHotel() throws HotelDuplicationException {
        CreateHotelDto hotelDemo = createHoteldemo();
        CreateHotelDto hotelDemo2 = createHoteldemo2();
        given()
                .body(hotelDemo)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().post(HOTEL_ENDPOINT)
                .then()
                .statusCode(201);

        given()
                .body(hotelDemo2)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().post(HOTEL_ENDPOINT)
                .then()
                .statusCode(201);
    }

    @Test
    void testAddHotelDuplicated() throws HotelDuplicationException {
        CreateHotelDto hotelDemo = createHoteldemo();
        hotelService.addHotel(hotelDemo);
        given()
                .body(hotelDemo)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().post(HOTEL_ENDPOINT)
                .then()
                .statusCode(400);

    }

    @Test
    void testAddInvalideHotel() {
        given()
                .body("{}")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().post(HOTEL_ENDPOINT)
                .then()
                .statusCode(400);
    }

    @Test
    void testAddInvalideHotel2() throws HotelDuplicationException {
        CreateHotelDto hotelDemo = createHoteldemo();
        hotelService.addHotel(hotelDemo);
        given()
                .body(Hotel.builder()
                        .hotelN("HotelTest")
                        .location("")
                        .phoneNumber("912345671")
                        .rooms(createRoomsSet())
                        .facilities(createFacilitiesSet())
                        .build())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().post(HOTEL_ENDPOINT)
                .then()
                .statusCode(400);
    }

    @Test
    void testGetAllHotelAfterAdd() throws HotelDuplicationException {
        CreateHotelDto hotelDemo = createHoteldemo();
        CreateHotelDto hotelDemo2 = createHoteldemo2();
        hotelService.addHotel(hotelDemo);
        hotelService.addHotel(hotelDemo2);

        given()
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().get(HOTEL_ENDPOINT + "?page=1")
                .then()
                .statusCode(200)
                .body("size()", is(2));
    }

    @Test
    void testUpdateRoomPrice() throws HotelDuplicationException {
        CreateHotelDto hotelDemo = createHoteldemo();
        hotelService.addHotel(hotelDemo);
        given()
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().put(HOTEL_ENDPOINT + "/updatePrice/HotelTest/101/200")
                .then()
                .statusCode(200);
    }

    @Test
    void testUpdateRoomPriceInvalprice() throws HotelDuplicationException {
        CreateHotelDto hotelDemo = createHoteldemo();
        hotelService.addHotel(hotelDemo);
        given()
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().put(HOTEL_ENDPOINT + "/updatePrice/HotelTest/101/50")
                .then()
                .statusCode(400);
    }

    @Test
    void testUpdateRoomPriceInvalHotel() throws HotelDuplicationException {
        CreateHotelDto hotelDemo = createHoteldemo();
        hotelService.addHotel(hotelDemo);
        given()
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().put(HOTEL_ENDPOINT + "/updatePrice/HotelT/101/200")
                .then()
                .statusCode(400);
    }

    @Test
    void testUpdateRoomPriceInvalRoom() throws HotelDuplicationException {
        CreateHotelDto hotelDemo = createHoteldemo();
        hotelService.addHotel(hotelDemo);
        given()
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().put(HOTEL_ENDPOINT + "/updatePrice/HotelTest/1000/200")
                .then()
                .statusCode(400);
    }

    @Test
    void testFindHotelByHotelN() throws HotelDuplicationException {
        CreateHotelDto hotelDemo = createHoteldemo();
        hotelService.addHotel(hotelDemo);
        given()
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().get(HOTEL_ENDPOINT + "/HotelName/HotelTest")
                .then()
                .statusCode(200);
    }

    @Test
    void testFindHotelByHotelNInvalidHotelN() throws HotelDuplicationException {
        CreateHotelDto hotelDemo = createHoteldemo();
        hotelService.addHotel(hotelDemo);
        given()
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().get(HOTEL_ENDPOINT + "/HotelName/HotelMarinho")
                .then()
                .statusCode(400);
    }

    @Test
    void testFindHotelsByAddress() throws HotelDuplicationException {
        CreateHotelDto hotelDemo = createHoteldemo();
        hotelService.addHotel(hotelDemo);
        given()
                .queryParam("page", 0)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().get(HOTEL_ENDPOINT + "/findByAddress/Sample Address")
                .then()
                .statusCode(200);
    }

    @Test
    void testFindHotelsByAddressInvalidAddress() throws HotelDuplicationException {
        CreateHotelDto hotelDemo = createHoteldemo();
        hotelService.addHotel(hotelDemo);
        given()
                .queryParam("page", 0)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().get(HOTEL_ENDPOINT + "/findByAddress/Samples")
                .then()
                .statusCode(400);
    }

}

