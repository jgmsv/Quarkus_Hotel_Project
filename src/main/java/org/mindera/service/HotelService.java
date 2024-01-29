package org.mindera.service;

import jakarta.ws.rs.PathParam;
import org.mindera.dto.CreateHotelDto;
import org.mindera.dto.CreateReservationDto;
import org.mindera.dto.HotelGetDto;
import org.mindera.model.Hotel;
import org.mindera.util.exceptions.HotelException;
import org.mindera.util.exceptions.RoomException;

import java.util.List;

public interface HotelService {


    Hotel addHotel(CreateHotelDto createHotelDto) throws HotelException;

    List<HotelGetDto> findAllHotels();

    HotelGetDto update(@PathParam("hotelN") String hotelN, @PathParam("roomNumber") int roomNumber, CreateReservationDto reservations) throws HotelException, RoomException;

    HotelGetDto findHotelByHotelN(@PathParam("hotelN") String hotelN) throws HotelException;
}
