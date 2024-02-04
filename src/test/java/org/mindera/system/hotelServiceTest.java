package org.mindera.system;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.mindera.dto.hotel.CreateHotelDto;
import org.mindera.dto.hotel.CreateRoomDto;
import org.mindera.model.hotel.Hotel;
import org.mindera.model.hotel.RoomType;
import org.mindera.repository.HotelRepository;

import java.util.Set;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class hotelServiceTest {

    @Inject
    HotelRepository hotelRepository;

    @Test
    public void testAddHotel() {
        // Given

        CreateRoomDto exe = new CreateRoomDto(1, 2, RoomType.SINGLEROOM, 100);

        CreateHotelDto createHotelDto = new CreateHotelDto("Test Hotel", "Test Location", 123456789,
                Set.of(new CreateRoomDto(1, 2, RoomType.SINGLEROOM, 100)));

        // When

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
        // Add more assertions based on your requirements
        // For example, you might want to check if the hotel properties match the DTO properties
        assertThat(hotel.getLocation(), is("Test Location"));
        assertThat(hotel.getPhoneNumber(), is(123456789));
        // Also, check if the room information is persisted
        assertThat(hotel.getRooms().size(), is(1));
        // Add more assertions for room details if needed
    }
}
