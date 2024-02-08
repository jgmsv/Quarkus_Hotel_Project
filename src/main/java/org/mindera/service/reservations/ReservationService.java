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

/**
 * The ReservationService interface provides methods for managing reservations.
 */
public interface ReservationService {
    /*Reservations addReservationToRoom(String hotelN, RoomType, CreateReservationDto reservation) throws HotelExistsException, RoomExistsException, InvalidDateReservationException, ReservationExistsException;*/

    /**
     * Adds a multi-room reservation.
     *
     * @param reservation the reservation to be added
     * @return the added reservation
     * @throws HotelExistsException       if the hotel already exists
     * @throws RoomExistsException        if the room already exists
     * @throws ReservationExistsException if the reservation already exists
     */
    ReservationsMulti addReservation(CreateReservationMultiDto reservation) throws HotelExistsException, RoomExistsException, ReservationExistsException;

    /**
     * Updates a multi-room reservation.
     *
     * @param reservationId     the ID of the reservation to be updated
     * @param updateReservation the updated reservation details
     * @return the updated reservation
     * @throws InvalidDateReservationException if the reservation dates are invalid
     * @throws RoomExistsException             if the room already exists
     * @throws HotelExistsException            if the hotel already exists
     * @throws ReservationExistsException      if the reservation already exists
     */
    public ReservationsMultiGetDto updateReservation(ObjectId reservationId, CreateReservationArrivaDeparturelDto updateReservation) throws InvalidDateReservationException, RoomExistsException, HotelExistsException, ReservationExistsException;

    /**
     * Retrieves all reservations.
     *
     * @param page the page number
     * @return a list of reservations
     */
    List<ReservationsMultiGetDto> findAllReservations(int page);

    /**
     * Retrieves a reservation by ID.
     *
     * @param id the ID of the reservation
     * @return the reservation with the specified ID
     */
    ReservationsMultiGetDto findReservationById(ObjectId id);

    /**
     * Checks all reservations.
     *
     * @return a list of reservations
     */
    List<ReservationsMulti> checkReservations();

    /**
     * Checks if a hotel room exists.
     *
     * @param hotelN     the hotel name
     * @param roomNumber the room number
     * @throws RoomExistsException  if the room already exists
     * @throws HotelExistsException if the hotel already exists
     */
    void checkHotelRoom(String hotelN, int roomNumber) throws RoomExistsException, HotelExistsException;

    /**
     * Checks reservations for a specific room in a hotel.
     *
     * @param hotelN     the hotel name
     * @param roomNumber the room number
     * @return a list of reservations for the specified room
     * @throws HotelExistsException if the hotel already exists
     * @throws RoomExistsException  if the room already exists
     */
    List<ReservationsMulti> checkRoomReservations(String hotelN, int roomNumber) throws HotelExistsException, RoomExistsException;

    /**
     * Checks if there is an overlapping reservation for a specific room in a hotel.
     *
     * @param hotelN     the hotel name
     * @param roomNumber the room number
     * @param arrival    the arrival date
     * @param departure  the departure date
     * @throws RoomExistsException        if the room already exists
     * @throws HotelExistsException       if the hotel already exists
     * @throws ReservationExistsException if the reservation already exists
     */
    void hasOverlappingReservation(String hotelN, int roomNumber, LocalDate arrival, LocalDate departure) throws RoomExistsException, HotelExistsException, ReservationExistsException;
}
