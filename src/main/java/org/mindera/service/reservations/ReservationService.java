package org.mindera.service.reservations;

import org.bson.types.ObjectId;
import org.mindera.dto.reservations.CreateReservationArrivaDeparturelDto;
import org.mindera.dto.reservations.CreateReservationDto;
import org.mindera.dto.reservations.ReservationsGetDto;
import org.mindera.model.reservations.Reservations;
import org.mindera.util.exceptions.hotel.HotelExistsException;
import org.mindera.util.exceptions.reservations.InvalidDateReservationException;
import org.mindera.util.exceptions.reservations.ReservationExistsException;
import org.mindera.util.exceptions.room.RoomExistsException;

import java.time.LocalDate;
import java.util.List;

public interface ReservationService {
    Reservations addReservationToRoom(String hotelN, int roomNumber, CreateReservationDto reservation) throws HotelExistsException, RoomExistsException, InvalidDateReservationException, ReservationExistsException;

    ReservationsGetDto updateReservation(ObjectId reservationId, CreateReservationArrivaDeparturelDto updateReservation) throws InvalidDateReservationException, RoomExistsException, HotelExistsException, ReservationExistsException;

    List<ReservationsGetDto> findAllReservations();

    ReservationsGetDto findReservationById(ObjectId id);

    List<Reservations> checkReservations();

    void checkHotelRoom(String hotelN, int roomNumber) throws RoomExistsException, HotelExistsException;

    List<Reservations> checkRoomReservations(String hotelN, int roomNumber) throws HotelExistsException, RoomExistsException;

    void hasOverlappingReservation(String hotelN, int roomNumber, LocalDate arrival, LocalDate departure) throws RoomExistsException, HotelExistsException, ReservationExistsException;
}
