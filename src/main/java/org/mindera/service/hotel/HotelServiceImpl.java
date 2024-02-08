package org.mindera.service.hotel;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.mindera.converter.hotel.HotelConverter;
import org.mindera.dto.hotel.CreateHotelDto;
import org.mindera.dto.hotel.HotelGetDto;
import org.mindera.model.hotel.Hotel;
import org.mindera.model.hotel.Rooms;
import org.mindera.repository.HotelRepository;
import org.mindera.util.exceptions.hotel.HotelAdressException;
import org.mindera.util.exceptions.hotel.HotelDuplicationException;
import org.mindera.util.exceptions.hotel.HotelExistsException;
import org.mindera.util.exceptions.room.RoomExistsException;
import org.mindera.util.exceptions.room.RoomPriceException;
import org.mindera.util.messages.MessagesExceptions;

import java.util.List;
import java.util.Optional;

import static org.mindera.converter.hotel.HotelConverter.*;

/**
 * Implementation of the HotelService interface.
 */
@ApplicationScoped
public class HotelServiceImpl implements HotelService {
    private final int PAGE_SIZE = 10;

    @Inject
    HotelRepository hotelRepository;

    /**
     * Adds a new hotel to the system.
     *
     * @param createHotelDto The DTO containing the information of the hotel to be created.
     * @return The created hotel.
     * @throws HotelDuplicationException If a hotel with the same hotelN already exists.
     */
    @Override
    public HotelGetDto addHotel(CreateHotelDto createHotelDto) throws HotelDuplicationException {
        Hotel hotel = dtoToHotel(createHotelDto);
        if (hotelRepository.isHotelNUnique(hotel.getHotelN())) {
            hotelRepository.persist(hotel);
        } else {
            throw new HotelDuplicationException(MessagesExceptions.DUPLICATEDHOTEL);
        }
        return HotelConverter.hotelToDto(hotel);
    }

    /**
     * Retrieves a list of all hotels.
     *
     * @param page The page number for pagination.
     * @return A list of HotelGetDto objects representing the hotels.
     * @throws HotelExistsException If no hotels are found.
     */
    @Override
    public List<HotelGetDto> findAllHotels(int page) {
        List<Hotel> hotels = hotelRepository.findAll().page(0, PAGE_SIZE).list();
        return hotelToDtoList(hotels);
    }

    /**
     * Retrieves a list of hotels based on the given address.
     *
     * @param location The address to search for.
     * @param page     The page number for pagination.
     * @return A list of HotelGetDto objects representing the hotels.
     * @throws HotelAdressException If no hotels are found with the given address.
     */
    @Override
    public List<HotelGetDto> findHotelsByAddress(String location, int page) throws HotelAdressException {
        List<Hotel> hotel = hotelRepository.findByAddress(location, page);

        if (hotel.isEmpty()) {
            throw new HotelAdressException(MessagesExceptions.HOTELADDRESSNOTFOUND);
        }
        ;
        return hotelToDtoList(hotel);
    }

    /**
     * Updates the price of a room in a hotel.
     *
     * @param hotelN     The hotelN of the hotel.
     * @param roomNumber The room number of the room.
     * @param price      The new price for the room.
     * @return The updated HotelGetDto object representing the hotel.
     * @throws HotelExistsException If the hotel with the given hotelN does not exist.
     * @throws RoomExistsException  If the room with the given room number does not exist in the hotel.
     * @throws RoomPriceException   If the new price is less than or equal to the current price of the room.
     */
    @Override
    public HotelGetDto updateRoomPrice(String hotelN, int roomNumber, int price) throws HotelExistsException, RoomExistsException, RoomPriceException {
        Hotel hotel = hotelRepository.findByHotelN(hotelN).orElseThrow(() -> new HotelExistsException(MessagesExceptions.HOTELERROR));
        Rooms roomToUpdate = hotel.getRooms().stream().filter(rooms -> rooms.getRoomNumber() == roomNumber).findFirst().orElseThrow(() -> new RoomExistsException(MessagesExceptions.ROOMERROR));
        if (price <= roomToUpdate.getRoomPrice()) {
            throw new RoomPriceException(MessagesExceptions.PRICEERROR);
        }
        roomToUpdate.setRoomPrice(price);
        hotelRepository.update(hotel);
        return hotelToDto(hotel);
    }

    /**
     * Retrieves a hotel based on the given hotelN.
     *
     * @param hotelN The hotelN to search for.
     * @return The HotelGetDto object representing the hotel.
     * @throws HotelExistsException If no hotel is found with the given hotelN.
     */
    @Override
    public HotelGetDto findHotelByHotelN(String hotelN) throws HotelExistsException {
        try {
            Hotel hotel = hotelRepository.findByHotelN(hotelN).orElseThrow(() -> new HotelExistsException(MessagesExceptions.HOTELERROR));
            return hotelToDto(hotel);
        } catch (HotelExistsException e) {
            throw e;
        }
    }

    /**
     * Retrieves a hotel based on the given hotelN.
     *
     * @param hotelN The hotelN to search for.
     * @return An Optional object containing the Hotel if found, or empty if not found.
     */
    public Optional<Hotel> findByHotelN(String hotelN) {
        return hotelRepository.findByHotelN(hotelN);
    }


}
