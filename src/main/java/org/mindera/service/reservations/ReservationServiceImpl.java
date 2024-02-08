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

@ApplicationScoped
public class ReservationServiceImpl implements ReservationService {
    private final int PAGE_SIZE = 10;

    @Inject
    HotelRepository hotelRepository;

    @Inject
    ReservationsMultiRepository reservationsMultiRepository;

    @Override
    public List<ReservationsMultiGetDto> findAllReservations(int page) {
        List<ReservationsMulti> reservationsMultis = reservationsMultiRepository.findAll().page(page, PAGE_SIZE).list();
        return reservationsMultiToDtoList(reservationsMultis);
    }

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

    @Override
    public ReservationsMultiGetDto findReservationById(ObjectId id) {
        ReservationsMulti reservations = reservationsMultiRepository.findById(id);
        return reservationMultiToDto(reservations);
    }

    @Override
    public List<ReservationsMulti> checkReservations() {
        List<ReservationsMulti> reservations = reservationsMultiRepository.listAll();
        if (reservations.isEmpty()) {
            return reservations.stream().toList();
        }
        return reservations;
    }

    @Override
    public void checkHotelRoom(String hotelN, int roomNumber) throws RoomExistsException, HotelExistsException {
        Hotel hotel = hotelRepository.findByHotelN(hotelN).orElseThrow(() -> new HotelExistsException(MessagesExceptions.HOTELERROR));
        Rooms room = hotel.getRooms().stream().filter(rooms -> rooms.getRoomNumber() == roomNumber).findFirst().orElseThrow(() -> new RoomExistsException(MessagesExceptions.ROOMERROR));
    }

    @Override
    public List<ReservationsMulti> checkRoomReservations(String hotelN, int roomNumber) throws HotelExistsException, RoomExistsException {
        checkHotelRoom(hotelN, roomNumber);
        List<ReservationsMulti> reservations = checkReservations().stream()
                .filter(reservation -> reservation.getHotelN().equals(hotelN) && reservation.getRoomReservations().stream().anyMatch(roomReservation -> roomReservation.getRoomNumber() == roomNumber)
                ).toList();
        return reservations == null ? Collections.emptyList() : reservations;
    }

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


