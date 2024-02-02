package org.mindera.service.reservations;

import org.bson.types.ObjectId;
import org.mindera.dto.reservations.CreateReservationDto;
import org.mindera.dto.reservations.ReservationsGetDto;
import org.mindera.model.hotel.RoomType;
import org.mindera.model.reservations.Reservations;
import org.mindera.util.exceptions.hotel.HotelExistsException;
import org.mindera.util.exceptions.reservations.InvalidDateReservationException;
import org.mindera.util.exceptions.reservations.ReservationExistsException;
import org.mindera.util.exceptions.room.RoomExistsException;

import java.time.LocalDate;
import java.util.List;

public interface ReservationService {
    Reservations addReservationToRoom(String hotelN, int roomNumber, CreateReservationDto reservation, LocalDate arrival, LocalDate departure) throws HotelExistsException, RoomExistsException, InvalidDateReservationException, ReservationExistsException;

    ReservationsGetDto updateArrival(ObjectId reservationId, LocalDate arrival) throws InvalidDateReservationException;

    ReservationsGetDto updateDeparture(ObjectId reservationId, LocalDate departure) throws InvalidDateReservationException;

    List<ReservationsGetDto> findAllReservations();

    ReservationsGetDto findReservationById(ObjectId id);

    public void checkRoomAvailability(String hotelN, ReservationsGetDto reservation, RoomType roomType, LocalDate arrival, LocalDate departure) throws InvalidDateReservationException, HotelExistsException, RoomExistsException, ReservationExistsException;
}
