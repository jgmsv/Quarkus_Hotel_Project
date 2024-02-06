package org.mindera.service.reservations;

import org.bson.types.ObjectId;
import org.mindera.dto.reservations.CreateReservationArrivaDeparturelDto;
import org.mindera.dto.reservations.CreateReservationMultiDto;
import org.mindera.dto.reservations.ReservationsMultiGetDto;
import org.mindera.model.reservations.ReservationsMulti;
import org.mindera.util.exceptions.hotel.HotelExistsException;
import org.mindera.util.exceptions.reservations.InvalidDateReservationException;
import org.mindera.util.exceptions.reservations.ReservationExistsException;
import org.mindera.util.exceptions.room.RoomExistsException;

import java.time.LocalDate;
import java.util.List;

public interface ReservationService {
    /*Reservations addReservationToRoom(String hotelN, RoomType, CreateReservationDto reservation) throws HotelExistsException, RoomExistsException, InvalidDateReservationException, ReservationExistsException;*/

    ReservationsMulti addReservation(CreateReservationMultiDto reservation) throws HotelExistsException, RoomExistsException, ReservationExistsException;

    public ReservationsMultiGetDto updateReservation(ObjectId reservationId, CreateReservationArrivaDeparturelDto updateReservation) throws InvalidDateReservationException, RoomExistsException, HotelExistsException, ReservationExistsException;

    List<ReservationsMultiGetDto> findAllReservations(int page);

    ReservationsMultiGetDto findReservationById(ObjectId id);

    List<ReservationsMulti> checkReservations();

    void checkHotelRoom(String hotelN, int roomNumber) throws RoomExistsException, HotelExistsException;

    List<ReservationsMulti> checkRoomReservations(String hotelN, int roomNumber) throws HotelExistsException, RoomExistsException;

    void hasOverlappingReservation(String hotelN, int roomNumber, LocalDate arrival, LocalDate departure) throws RoomExistsException, HotelExistsException, ReservationExistsException;
}
