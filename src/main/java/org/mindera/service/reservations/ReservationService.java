package org.mindera.service.reservations;

import org.bson.types.ObjectId;
import org.mindera.dto.reservations.CreateReservationCheckOutDto;
import org.mindera.dto.reservations.CreateReservationDto;
import org.mindera.dto.reservations.ReservationsGetDto;
import org.mindera.model.reservations.Reservations;
import org.mindera.util.exceptions.hotel.HotelExistsException;
import org.mindera.util.exceptions.reservations.InvalidDateReservationException;
import org.mindera.util.exceptions.room.RoomExistsException;

import java.util.List;

public interface ReservationService {
    Reservations addReservationToRoom(String hotelN, int roomNumber, CreateReservationDto reservation) throws HotelExistsException, RoomExistsException, InvalidDateReservationException;

    ReservationsGetDto updateCheckOutDate(String hotelN, int roomNumber, CreateReservationCheckOutDto reservation, ObjectId reservationId) throws HotelExistsException, RoomExistsException, InvalidDateReservationException;

    List<ReservationsGetDto> findAllReservations();

    ReservationsGetDto findReservationById(ObjectId id);
}
