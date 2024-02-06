package org.mindera.system;

import com.mongodb.client.MongoClient;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mindera.dto.hotel.CreateHotelDto;
import org.mindera.dto.hotel.CreateRoomDto;
import org.mindera.model.hotel.Hotel;
import org.mindera.model.hotel.RoomType;
import org.mindera.repository.HotelRepository;
import org.mindera.service.hotel.HotelService;
import org.mindera.util.exceptions.hotel.HotelDuplicationException;
import org.mindera.util.exceptions.hotel.HotelExistsException;

import java.util.Set;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class hotelServiceTest {

    @Inject
    MongoClient mongoClient;
    @Inject
    HotelRepository hotelRepository;
    @Inject
    HotelService hotelService;

    @AfterEach
    public void setUp() {
        mongoClient.getDatabase("hoteldb").getCollection("Hotels").drop();

    }


    @Test
    public void testAddHotel() throws HotelExistsException, HotelDuplicationException {
        // Given
        CreateHotelDto createHotelDto = new CreateHotelDto("Test Hotel", "Test Location", 123456789,
                Set.of(new CreateRoomDto(1, 2, RoomType.SINGLEROOM, 100)));
        // When
        try {
            hotelService.addHotel(createHotelDto);
        } catch (HotelExistsException e) {
            // Then
            Hotel hotel = hotelRepository.findByHotelN("Test Hotel").orElse(null);
            assert hotel != null;
            assertThat(hotel.getLocation(), is("Test Location"));
            assertThat(hotel.getPhoneNumber(), is(123456789));
            assertThat(hotel.getRooms().size(), is(1));

        }
    }

    @Test
    public void testFindAllHotels() throws HotelExistsException {
        // Given
        CreateHotelDto createHotelDto = new CreateHotelDto("Test Hotel", "Test Location", 123456789,
                Set.of(new CreateRoomDto(1, 2, RoomType.SINGLEROOM, 100)));
        try {
            hotelService.addHotel(createHotelDto);
        } catch (HotelDuplicationException e) {
            // When
            given()
                    .contentType(ContentType.JSON)
                    .when().get("/api/v1/hotel")
                    .then()
                    .statusCode(200);
            // Then
            Hotel hotel = hotelRepository.findByHotelN("Test Hotel").orElse(null);
            assert hotel != null;
            assertThat(hotel.getLocation(), is("Test Location"));
            assertThat(hotel.getPhoneNumber(), is(123456789));
            assertThat(hotel.getRooms().size(), is(1));
        }
    }

    @Test
    public void testFindAllHotels() {
        // Given
        CreateHotelDto createHotelDto = new CreateHotelDto("Test Hotel", "Test Location", 123456789,
                Set.of(new CreateRoomDto(1, 2, RoomType.SINGLEROOM, 100)));
        try {
            hotelService.addHotel(createHotelDto);
        } catch (HotelDuplicationException e) {
            // When
            given()
                    .contentType(ContentType.JSON)
                    .when().get("/api/v1/hotel")
                    .then()
                    .statusCode(200);
            // Then
            Hotel hotel = hotelRepository.findByHotelN("Test Hotel").orElse(null);
            assert hotel != null;
            assertThat(hotel.getLocation(), is("Test Location"));
            assertThat(hotel.getPhoneNumber(), is(123456789));
            assertThat(hotel.getRooms().size(), is(1));
        }
    }

    @Test
    public void testUpdateRoomPrice() throws HotelExistsException, HotelDuplicationException {
        // Given
        CreateHotelDto createHotelDto = new CreateHotelDto("Test Hotel", "Test Location", 123456789,
                Set.of(new CreateRoomDto(1, 2, RoomType.SINGLEROOM, 100)));
        try {
            hotelService.addHotel(createHotelDto);
        } catch (HotelDuplicationException e) {
            // When
            given()
                    .contentType(ContentType.JSON)
                    .when().put("/api/v1/hotel/Test Hotel/1/200")
                    .then()
                    .statusCode(200);
            // Then
            Hotel hotel = hotelRepository.findByHotelN("Test Hotel").orElse(null);
            assert hotel != null;
            assertThat(hotel.getLocation(), is("Test Location"));
            assertThat(hotel.getPhoneNumber(), is(123456789));
            assertThat(hotel.getRooms().size(), is(1));
            assertThat(hotel.getRooms().stream().findFirst().get().getRoomPrice(), is(200));
        }
    }
}

