package org.mindera.system;

import com.mongodb.client.MongoClient;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mindera.dto.hotel.CreateHotelDto;
import org.mindera.dto.hotel.CreateRoomDto;
import org.mindera.dto.hotel.HotelGetDto;
import org.mindera.model.hotel.Hotel;
import org.mindera.model.hotel.RoomType;
import org.mindera.repository.HotelRepository;
import org.mindera.service.hotel.HotelService;
import org.mindera.util.exceptions.hotel.HotelDuplicationException;
import org.mindera.util.exceptions.hotel.HotelExistsException;
import org.mindera.util.exceptions.room.RoomExistsException;
import org.mindera.util.exceptions.room.RoomPriceException;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

    //TODO: check validation constraints
    //TODO: check exception and messages

    @Test
    public void testAddHotel() throws HotelDuplicationException, HotelExistsException {
        // Given
        CreateHotelDto createHotelDto = new CreateHotelDto(
                "HotelTest",
                "Sample Address",
                "912345671",
                Set.of(
                        new CreateRoomDto(101, 1, RoomType.SINGLEROOM, 100),
                        new CreateRoomDto(102, 2, RoomType.TRIPLEROOM, 150),
                        new CreateRoomDto(103, 3, RoomType.DELUXEROOM, 200)
                ));

        // When
        Hotel addedHotel = hotelService.addHotel(createHotelDto);

        // Then
        assertNotNull(addedHotel.id); // Check if ID is set
        assertEquals("HotelTest", addedHotel.getHotelN());
        assertEquals("Sample Address", addedHotel.getLocation());
        assertEquals(3, addedHotel.getRooms().size());
        assertEquals(103, addedHotel.getRooms().stream().filter(hotels -> hotels.getRoomNumber() == 103).findFirst().get().getRoomNumber());
    }

    @Test
    void testFindAllHotels() throws HotelDuplicationException, HotelExistsException {
        // Given
        CreateHotelDto createHotelDto = new CreateHotelDto(
                "HotelTest",
                "Sample Address",
                "912345671",
                Set.of(
                        new CreateRoomDto(101, 1, RoomType.SINGLEROOM, 100),
                        new CreateRoomDto(102, 2, RoomType.TRIPLEROOM, 150),
                        new CreateRoomDto(103, 3, RoomType.DELUXEROOM, 200)
                ));
        hotelService.addHotel(createHotelDto);

        // When
        List<HotelGetDto> hotels = hotelService.findAllHotels();

        // Then
        assertEquals(1, hotels.size());
        assertEquals("HotelTest", hotels.get(0).hotelN());
        assertEquals("Sample Address", hotels.get(0).location());
        assertEquals(3, hotels.get(0).rooms().size());
        assertEquals(102, hotels.get(0).rooms().stream().filter(hotels1 -> hotels1.getRoomNumber() == 102).findFirst().get().getRoomNumber());
    }

    @Test
    public void testUpdateRoomPrice() throws HotelDuplicationException, HotelExistsException, RoomExistsException, RoomPriceException {
        // Given
        CreateHotelDto createHotelDto = new CreateHotelDto(
                "HotelTest",
                "Sample Address",
                "912345671",
                Set.of(
                        new CreateRoomDto(101, 1, RoomType.SINGLEROOM, 100),
                        new CreateRoomDto(102, 2, RoomType.TRIPLEROOM, 150),
                        new CreateRoomDto(103, 3, RoomType.DELUXEROOM, 200)
                ));
        hotelService.addHotel(createHotelDto);

        // When
        HotelGetDto updatedHotel = hotelService.updateRoomPrice("HotelTest", 102, 200);

        // Then
        assertEquals(200, updatedHotel.rooms().stream().filter(hotels1 -> hotels1.getRoomNumber() == 102).findFirst().get().getRoomPrice());
    }

    @Test
    public void testFindHotelByHotelN() throws HotelDuplicationException, HotelExistsException {
        // Given
        CreateHotelDto createHotelDto = new CreateHotelDto(
                "HotelTest",
                "Sample Address",
                "912345671",
                Set.of(
                        new CreateRoomDto(101, 1, RoomType.SINGLEROOM, 100),
                        new CreateRoomDto(102, 2, RoomType.TRIPLEROOM, 150),
                        new CreateRoomDto(103, 3, RoomType.DELUXEROOM, 200)
                ));
        hotelService.addHotel(createHotelDto);

        // When
        HotelGetDto hotel = hotelService.findHotelByHotelN("HotelTest");

        // Then
        assertEquals("HotelTest", hotel.hotelN());
        assertEquals("Sample Address", hotel.location());
        assertEquals(3, hotel.rooms().size());
        assertEquals(102, hotel.rooms().stream().filter(hotels1 -> hotels1.getRoomNumber() == 102).findFirst().get().getRoomNumber());
    }

}

