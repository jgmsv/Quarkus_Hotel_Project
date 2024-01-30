package org.mindera.service;

import jakarta.ws.rs.PathParam;
import org.mindera.dto.CreateHotelDto;
import org.mindera.dto.CreateReservationCheckInDto;
import org.mindera.dto.CreateReservationCheckOutDto;
import org.mindera.dto.HotelGetDto;
import org.mindera.model.Hotel;
import org.mindera.util.exceptions.HotelDuplication;
import org.mindera.util.exceptions.HotelException;
import org.mindera.util.exceptions.RoomException;

import java.util.List;

public interface HotelService {


    Hotel addHotel(CreateHotelDto createHotelDto) throws HotelException, HotelDuplication;

    List<HotelGetDto> findAllHotels();

    HotelGetDto updateRoomCheckInDate(@PathParam("hotelN") String hotelN, @PathParam("roomNumber") int roomNumber, CreateReservationCheckInDto reservations) throws HotelException, RoomException;

    HotelGetDto updateRoomCheckOutDate(@PathParam("hotelN") String hotelN, @PathParam("roomNumber") int roomNumber, CreateReservationCheckOutDto reservations) throws HotelException, RoomException;

    HotelGetDto findHotelByHotelN(@PathParam("hotelN") String hotelN) throws HotelException;

    HotelGetDto updateRoomPrice(@PathParam("hotelN") String hotelN, @PathParam("roomNumber") int roomNumber, int price) throws HotelException, RoomException;
}
