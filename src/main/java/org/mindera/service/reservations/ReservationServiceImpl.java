package org.mindera.service.reservations;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.types.ObjectId;
import org.mindera.converter.reservations.ReservationConverter;
import org.mindera.dto.reservations.CreateReservationArrivaDeparturelDto;
import org.mindera.dto.reservations.CreateReservationMultiDto;
import org.mindera.dto.reservations.CreateRoomsReservationDto;
import org.mindera.dto.reservations.ReservationsMultiGetDto;
import org.mindera.model.hotel.Hotel;
import org.mindera.model.hotel.RoomType;
import org.mindera.model.hotel.Rooms;
import org.mindera.model.reservations.ReservationsMulti;
import org.mindera.model.reservations.RoomReservations;
import org.mindera.repository.HotelRepository;
import org.mindera.repository.ReservationsMultiRepository;
import org.mindera.util.exceptions.hotel.HotelExistsException;
import org.mindera.util.exceptions.reservations.InvalidDateReservationException;
import org.mindera.util.exceptions.reservations.ReservationExistsException;
import org.mindera.util.exceptions.room.RoomExistsException;
import org.mindera.util.messages.MessagesExceptions;

import java.time.LocalDate;
import java.util.*;

import static org.mindera.converter.reservations.ReservationConverter.reservationMultiToDto;
import static org.mindera.converter.reservations.ReservationConverter.reservationsMultiToDtoList;

/**
 * Implementation of the ReservationService interface.
 * Provides methods for managing reservations.
 */
@ApplicationScoped
public class ReservationServiceImpl implements ReservationService {
    private final int PAGE_SIZE = 10;

    @Inject
    HotelRepository hotelRepository;

    @Inject
    ReservationsMultiRepository reservationsMultiRepository;

    /**
     * Retrieves a list of reservations with pagination.
     *
     * @param page The page number to retrieve.
     * @return A list of ReservationsMultiGetDto objects representing the reservations.
     */
    @Override
    public List<ReservationsMultiGetDto> findAllReservations(int page) {
        List<ReservationsMulti> reservationsMultis = reservationsMultiRepository.findAll().page(page, PAGE_SIZE).list();
        return reservationsMultiToDtoList(reservationsMultis);
    }

    /**
     * Represents a DTO (Data Transfer Object) for retrieving multiple reservations.
     * This DTO contains information about the reservations, such as arrival and departure dates.
     */
    @Override
    public ReservationsMultiGetDto updateReservation(ObjectId reservationId, CreateReservationArrivaDeparturelDto updateReservation) throws InvalidDateReservationException, RoomExistsException, HotelExistsException, ReservationExistsException {
        ReservationsMulti reservationMulti = reservationsMultiRepository.findById(reservationId);

        ReservationsMulti reservationUpdate = ReservationConverter.dtoToArrivalDeparture(updateReservation);

        if (reservationUpdate.getArrival().isAfter(reservationUpdate.getDeparture())) {
            throw new InvalidDateReservationException(MessagesExceptions.INVALIDDATE);
        }

        for (RoomReservations roomReservation : reservationMulti.getRoomReservations()) {
            hasOverlappingReservation(reservationMulti.getHotelN(), roomReservation.getRoomNumber(), reservationUpdate.getArrival(), reservationUpdate.getDeparture());
        }


        reservationMulti.setArrival(updateReservation.arrival());
        reservationMulti.setDeparture(updateReservation.departure());

        reservationsMultiRepository.update(reservationMulti);

        return reservationMultiToDto(reservationMulti);
    }

    /**
     * Finds a reservation by its ID and returns it as a ReservationsMultiGetDto object.
     *
     * @param id the ID of the reservation to find
     * @return the reservation as a ReservationsMultiGetDto object
     */
    @Override
    public ReservationsMultiGetDto findReservationById(ObjectId id) {
        ReservationsMulti reservations = reservationsMultiRepository.findById(id);
        return reservationMultiToDto(reservations);
    }

    /**
     * Retrieves a list of all reservations.
     * <p>
     * This method retrieves all reservations stored in the system.
     *
     * @return A list of {@code ReservationsMulti} objects representing the reservations.
     * If no reservations are found, an empty list is returned.
     */
    @Override
    public List<ReservationsMulti> checkReservations() {
        List<ReservationsMulti> reservations = reservationsMultiRepository.listAll();
        if (reservations.isEmpty()) {
            return reservations.stream().toList();
        }
        return reservations;
    }

    /**
     * Checks if a hotel room exists in the specified hotel.
     *
     * @param hotelN     The name of the hotel.
     * @param roomNumber The room number to check.
     * @throws RoomExistsException  If the room does not exist.
     * @throws HotelExistsException If the hotel does not exist.
     */
    @Override
    public void checkHotelRoom(String hotelN, int roomNumber) throws RoomExistsException, HotelExistsException {
        Hotel hotel = hotelRepository.findByHotelN(hotelN).orElseThrow(() -> new HotelExistsException(MessagesExceptions.HOTELERROR));
        Rooms room = hotel.getRooms().stream().filter(rooms -> rooms.getRoomNumber() == roomNumber).findFirst().orElseThrow(() -> new RoomExistsException(MessagesExceptions.ROOMERROR));
    }

    /**
     * Checks the reservations for a specific room in a hotel.
     *
     * @param hotelN     the name of the hotel
     * @param roomNumber the room number
     * @return a list of ReservationsMulti objects representing the reservations for the specified room
     * @throws HotelExistsException if the hotel does not exist
     * @throws RoomExistsException  if the room does not exist
     */
    @Override
    public List<ReservationsMulti> checkRoomReservations(String hotelN, int roomNumber) throws HotelExistsException, RoomExistsException {
        checkHotelRoom(hotelN, roomNumber);
        List<ReservationsMulti> reservations = checkReservations().stream()
                .filter(reservation -> reservation.getHotelN().equals(hotelN) && reservation.getRoomReservations().stream().anyMatch(roomReservation -> roomReservation.getRoomNumber() == roomNumber)
                ).toList();
        return reservations == null ? Collections.emptyList() : reservations;
    }

    /**
     * Checks if there is any overlapping reservation for a given hotel, room number, arrival date, and departure date.
     * Throws ReservationExistsException if an overlapping reservation is found.
     *
     * @param hotelN     the hotel name
     * @param roomNumber the room number
     * @param arrival    the arrival date
     * @param departure  the departure date
     * @throws RoomExistsException        if the room does not exist
     * @throws HotelExistsException       if the hotel does not exist
     * @throws ReservationExistsException if an overlapping reservation is found
     */
    @Override
    public void hasOverlappingReservation(String hotelN, int roomNumber, LocalDate arrival, LocalDate departure) throws RoomExistsException, HotelExistsException, ReservationExistsException {
        List<ReservationsMulti> reservations = checkRoomReservations(hotelN, roomNumber);
        for (ReservationsMulti existingReservation : reservations) {
            LocalDate existingArrival = existingReservation.getArrival();
            LocalDate existingDeparture = existingReservation.getDeparture();


            boolean isOverlap =
                    arrival.isBefore(existingDeparture) &&
                            departure.isAfter(existingArrival);

            if (isOverlap) {
                throw new ReservationExistsException(MessagesExceptions.RESERVATIONEXISTS);
            }
        }
    }

    /**
     * Adds a new reservation to the system.
     * <p>
     * This method adds a new reservation based on the provided reservation DTO.
     * It checks for available rooms in the specified hotel, validates the reservation dates,
     * and ensures there are no overlapping reservations for the selected rooms.
     * If all checks pass, the reservation is added to the system.
     *
     * @param reservation The reservation DTO containing the details of the reservation to be added.
     * @return The newly created {@code ReservationsMulti} object representing the added reservation.
     * @throws HotelExistsException       If the specified hotel does not exist in the system.
     * @throws RoomExistsException        If there are no available rooms of the requested type or if the requested room does not exist.
     * @throws ReservationExistsException If there is an overlapping reservation for the selected rooms during the specified dates.
     */
    @Override
    public ReservationsMulti addReservation(CreateReservationMultiDto reservation)
            throws HotelExistsException, RoomExistsException, ReservationExistsException {

        Hotel hotel = hotelRepository.findByHotelN(reservation.hotelN())
                .orElseThrow(() -> new HotelExistsException(MessagesExceptions.HOTELERROR));

        Set<Rooms> roomsSet = hotel.getRooms();
        List<Rooms> rooms = new ArrayList<>(roomsSet);
        List<CreateRoomsReservationDto> roomReservations = reservation.roomReservations();
        List<RoomReservations> reservations = new ArrayList<>();

        Map<RoomType, List<Rooms>> availableRoomsMap = new HashMap<>();
        for (Rooms room : rooms) {
            availableRoomsMap.computeIfAbsent(room.getRoomType(), k -> new ArrayList<>()).add(room);
        }


        for (CreateRoomsReservationDto dtoRooms : roomReservations) {
            RoomType requestedRoomType = dtoRooms.roomType();
            List<Rooms> availableRooms = availableRoomsMap.get(requestedRoomType);
            if (availableRooms != null && !availableRooms.isEmpty()) {

                Rooms room = availableRooms.remove(0);
                hasOverlappingReservation(reservation.hotelN(), room.getRoomNumber(), reservation.arrival(), reservation.departure());
                reservations.add(new RoomReservations(room.getRoomNumber(), requestedRoomType));
            } else {
                throw new RoomExistsException(MessagesExceptions.ROOMERROR);
            }
        }

        for (RoomReservations roomReservation : reservations) {
            hasOverlappingReservation(reservation.hotelN(), roomReservation.getRoomNumber(), reservation.arrival(), reservation.departure());
        }


        ReservationsMulti reservationUpdate = ReservationConverter.dtoToReservations(reservation);

        reservationUpdate.setHotelN(reservation.hotelN());
        reservationUpdate.setArrival(reservation.arrival());
        reservationUpdate.setDeparture(reservation.departure());
        reservationUpdate.setRoomReservations(reservations);

        reservationsMultiRepository.persist(reservationUpdate);

        return reservationUpdate;
    }
}


