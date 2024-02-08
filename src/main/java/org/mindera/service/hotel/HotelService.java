package org.mindera.service.hotel;

import org.mindera.dto.hotel.CreateHotelDto;
import org.mindera.dto.hotel.HotelGetDto;
import org.mindera.util.exceptions.hotel.HotelAdressException;
import org.mindera.util.exceptions.hotel.HotelDuplicationException;
import org.mindera.util.exceptions.hotel.HotelExistsException;
import org.mindera.util.exceptions.room.RoomExistsException;
import org.mindera.util.exceptions.room.RoomPriceException;

import java.util.List;

/**
 * The HotelService interface provides methods for managing hotels.
 */
public interface HotelService {
    /**
     * Adds a new hotel.
     *
     * @param createHotelDto the DTO containing the hotel information
     * @return the added hotel
     * @throws HotelDuplicationException if a hotel with the same name already exists
     */
    HotelGetDto addHotel(CreateHotelDto createHotelDto) throws HotelDuplicationException;

    /**
     * Retrieves a list of all hotels.
     *
     * @param page the page number for pagination
     * @return a list of hotel DTOs
     * @throws HotelExistsException if no hotels exist
     */
    List<HotelGetDto> findAllHotels(int page);

    /**
     * Retrieves a hotel by its hotel number.
     *
     * @param hotelN the hotel number
     * @return the hotel DTO
     * @throws HotelExistsException if the hotel does not exist
     */
    HotelGetDto findHotelByHotelN(String hotelN) throws HotelExistsException;

    /**
     * Updates the price of a room in a hotel.
     *
     * @param hotelN     the hotel number
     * @param roomNumber the room number
     * @param price      the new price
     * @return the updated hotel DTO
     * @throws HotelExistsException if the hotel does not exist
     * @throws RoomExistsException  if the room does not exist
     * @throws RoomPriceException   if the price is invalid
     */
    HotelGetDto updateRoomPrice(String hotelN, int roomNumber, int price) throws HotelExistsException, RoomExistsException, RoomPriceException;

    /**
     * Retrieves a list of hotels by their address.
     *
     * @param location the address location
     * @param page     the page number for pagination
     * @return a list of hotel DTOs
     * @throws HotelAdressException if the address is invalid
     */
    List<HotelGetDto> findHotelsByAddress(String location, int page) throws HotelAdressException;
}
