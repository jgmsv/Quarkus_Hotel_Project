package org.mindera.service;

import jakarta.ws.rs.PathParam;
import org.mindera.dto.CreateHotelDto;
import org.mindera.dto.CreateReservationCheckInDto;
import org.mindera.dto.CreateReservationCheckOutDto;
import org.mindera.dto.HotelGetDto;
import org.mindera.model.Hotel;
import org.mindera.util.exceptions.hotel.HotelDuplicationException;
import org.mindera.util.exceptions.hotel.HotelExistsException;
import org.mindera.util.exceptions.reservations.InvalidDateReservation;
import org.mindera.util.exceptions.room.RoomExistsException;
import org.mindera.util.exceptions.room.RoomPriceException;

import java.util.List;

public interface HotelService {


    Hotel addHotel(CreateHotelDto createHotelDto) throws HotelExistsException, HotelDuplicationException;

    List<HotelGetDto> findAllHotels();

    HotelGetDto updateRoomCheckInDate(@PathParam("hotelN") String hotelN, @PathParam("roomNumber") int roomNumber, CreateReservationCheckInDto reservations) throws HotelExistsException, RoomExistsException, InvalidDateReservation;

    HotelGetDto updateRoomCheckOutDate(@PathParam("hotelN") String hotelN, @PathParam("roomNumber") int roomNumber, CreateReservationCheckOutDto reservations) throws HotelExistsException, RoomExistsException, InvalidDateReservation;

    HotelGetDto findHotelByHotelN(@PathParam("hotelN") String hotelN) throws HotelExistsException;

    HotelGetDto updateRoomPrice(@PathParam("hotelN") String hotelN, @PathParam("roomNumber") int roomNumber, int price) throws HotelExistsException, RoomExistsException, RoomPriceException;

}
