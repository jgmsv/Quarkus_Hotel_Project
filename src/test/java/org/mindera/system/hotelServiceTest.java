package org.mindera.system;

import com.mongodb.client.MongoClient;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindera.dto.hotel.CreateHotelDto;
import org.mindera.dto.hotel.CreateRoomDto;
import org.mindera.model.hotel.Hotel;
import org.mindera.model.hotel.RoomType;
import org.mindera.repository.HotelRepository;
import org.mindera.service.hotel.HotelService;

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

    @BeforeEach
    public void setUp() {
        mongoClient.getDatabase("hoteldb").getCollection("Hotels").drop();

    }


    @Test
    public void testAddHotel() {
        // Given

        CreateRoomDto exe = new CreateRoomDto(1, 2, RoomType.SINGLEROOM, 100);

        CreateHotelDto createHotelDto = new CreateHotelDto("Test Hotel", "Test Location", 123456789,
                Set.of(new CreateRoomDto(1, 2, RoomType.SINGLEROOM, 100)));
        // When
        given()
                .contentType(ContentType.JSON)
                .body(createHotelDto)
                .when().post("/api/v1/hotel")
                .then()
                .statusCode(200);
        // Then
        Hotel hotel = hotelRepository.findByHotelN("Test Hotel").orElse(null);
        assert hotel != null;
        assertThat(hotel.getLocation(), is("Test Location"));
        assertThat(hotel.getPhoneNumber(), is(123456789));
        assertThat(hotel.getRooms().size(), is(1));

    }

    @Test
    public void testAddHotelDuplicate() {
        CreateHotelDto createHotelDto = new CreateHotelDto("Duplicate Hotel", "Location", 123456789, null);
        Hotel duplicateHotel = Hotel.builder().hotelN("Duplicate Hotel").location("Location").phoneNumber(123456789).rooms(null).build();


    }
}
