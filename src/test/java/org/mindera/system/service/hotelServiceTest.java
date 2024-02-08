package org.mindera.system.service;

import com.mongodb.client.MongoClient;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindera.dto.hotel.CreateHotelDto;
import org.mindera.dto.hotel.CreateRoomDto;
import org.mindera.dto.hotel.HotelGetDto;
import org.mindera.model.hotel.Facilities;
import org.mindera.model.hotel.RoomType;
import org.mindera.repository.HotelRepository;
import org.mindera.service.hotel.HotelService;
import org.mindera.util.exceptions.hotel.HotelAdressException;
import org.mindera.util.exceptions.hotel.HotelDuplicationException;
import org.mindera.util.exceptions.hotel.HotelExistsException;
import org.mindera.util.exceptions.room.RoomExistsException;
import org.mindera.util.exceptions.room.RoomPriceException;
import org.mindera.util.messages.MessagesExceptions;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

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
                ),
                Set.of(Facilities.BAR, Facilities.SPA, Facilities.EXTERIORSWIMMINGPOOL));

        // When
        HotelGetDto addedHotel = hotelService.addHotel(createHotelDto);

        // Then
        assertEquals("HotelTest", addedHotel.hotelN());
        assertEquals("Sample Address", addedHotel.location());
        assertEquals(3, addedHotel.rooms().size());
        assertEquals(103, addedHotel.rooms().stream().filter(hotels -> hotels.getRoomNumber() == 103).findFirst().get().getRoomNumber());
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
                ),
                Set.of(Facilities.BAR, Facilities.SPA, Facilities.EXTERIORSWIMMINGPOOL));
        hotelService.addHotel(createHotelDto);

        // When
        List<HotelGetDto> hotels = hotelService.findAllHotels(1);

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
                ),
                Set.of(Facilities.BAR, Facilities.SPA, Facilities.EXTERIORSWIMMINGPOOL));
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
                ),
                Set.of(Facilities.BAR, Facilities.SPA, Facilities.EXTERIORSWIMMINGPOOL));
        hotelService.addHotel(createHotelDto);

        // When
        HotelGetDto hotel = hotelService.findHotelByHotelN("HotelTest");

        // Then
        assertEquals("HotelTest", hotel.hotelN());
        assertEquals("Sample Address", hotel.location());
        assertEquals(3, hotel.rooms().size());
        assertEquals(102, hotel.rooms().stream().filter(hotels1 -> hotels1.getRoomNumber() == 102).findFirst().get().getRoomNumber());
    }


    @Test
    public void testAddHotel_DuplicateHotel() throws HotelDuplicationException {


        CreateHotelDto createHotelDto = new CreateHotelDto("Strada", "Porto", "234181306",
                Set.of(new CreateRoomDto(101, 1, RoomType.SINGLEROOM, 100),
                        new CreateRoomDto(102, 2, RoomType.TRIPLEROOM, 150)),
                Set.of(Facilities.BAR, Facilities.SPA, Facilities.EXTERIORSWIMMINGPOOL));


        hotelService.addHotel(createHotelDto);


        CreateHotelDto createHotelDto2 = new CreateHotelDto("Strada", "Porto", "234181306",
                Set.of(new CreateRoomDto(101, 1, RoomType.SINGLEROOM, 100),
                        new CreateRoomDto(102, 2, RoomType.TRIPLEROOM, 150)),
                Set.of(Facilities.BAR, Facilities.SPA, Facilities.EXTERIORSWIMMINGPOOL));


        HotelDuplicationException exception = assertThrows(HotelDuplicationException.class, () -> hotelService.addHotel(createHotelDto2));
        assertTrue(exception.getMessage().contains(MessagesExceptions.DUPLICATEDHOTEL));
    }

    @Test
    public void testFindHotelsByAddress_HotelNotFound() throws HotelDuplicationException {
        CreateHotelDto createHotelDto = new CreateHotelDto("Strada", "Porto", "234181306",
                Set.of(new CreateRoomDto(101, 1, RoomType.SINGLEROOM, 100),
                        new CreateRoomDto(102, 2, RoomType.TRIPLEROOM, 150)),
                Set.of(Facilities.BAR, Facilities.SPA, Facilities.EXTERIORSWIMMINGPOOL));
        hotelService.addHotel(createHotelDto);
        String address = "NonExistingAddress";

        HotelAdressException exception = assertThrows(HotelAdressException.class, () -> hotelService.findHotelsByAddress(address, 0));
        assertTrue(exception.getMessage().contains(MessagesExceptions.HOTELADDRESSNOTFOUND));
    }


    @Test
    public void testUpdateRoomPrice_Exceptions() throws HotelDuplicationException {
        CreateHotelDto createHotelDto = new CreateHotelDto("Strada", "Porto", "234181306",
                Set.of(new CreateRoomDto(101, 1, RoomType.SINGLEROOM, 100)),
                Set.of(Facilities.BAR, Facilities.SPA, Facilities.EXTERIORSWIMMINGPOOL));
        hotelService.addHotel(createHotelDto);

        HotelExistsException exception1 = assertThrows(HotelExistsException.class, () -> hotelService.updateRoomPrice("NonExistingHotel", 102, 90));
        assertTrue(exception1.getMessage().contains(MessagesExceptions.HOTELERROR));

        RoomExistsException exception2 = assertThrows(RoomExistsException.class, () -> hotelService.updateRoomPrice("Strada", 100, 90));
        assertTrue(exception2.getMessage().contains(MessagesExceptions.ROOMERROR));

        RoomPriceException exception3 = assertThrows(RoomPriceException.class, () -> hotelService.updateRoomPrice("Strada", 101, 50));
        assertTrue(exception3.getMessage().contains(MessagesExceptions.PRICEERROR));
    }

}

