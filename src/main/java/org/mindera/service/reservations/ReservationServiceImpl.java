package org.mindera.service.reservations;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.types.ObjectId;
import org.mindera.converter.reservations.ReservationConverter;
import org.mindera.dto.reservations.*;
import org.mindera.model.hotel.Hotel;
import org.mindera.model.hotel.Rooms;
import org.mindera.model.reservations.Reservations;
import org.mindera.model.reservations.ReservationsMulti;
import org.mindera.model.reservations.RoomReservations;
import org.mindera.repository.HotelRepository;
import org.mindera.repository.ReservationRepository;
import org.mindera.repository.ReservationsMultiRepository;
import org.mindera.util.exceptions.hotel.HotelExistsException;
import org.mindera.util.exceptions.reservations.InvalidDateReservationException;
import org.mindera.util.exceptions.reservations.ReservationExistsException;
import org.mindera.util.exceptions.room.RoomExistsException;
import org.mindera.util.messages.MessagesExceptions;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mindera.converter.reservations.ReservationConverter.reservationsMultiToDtoList;
import static org.mindera.converter.reservations.ReservationConverter.reservationsToDto;

@ApplicationScoped
public class ReservationServiceImpl implements ReservationService {
    private final int PAGE_SIZE = 10;

    @Inject
    HotelRepository hotelRepository;
    @Inject
    ReservationRepository reservationRepository;
    @Inject
    ReservationsMultiRepository reservationsMultiRepository;

    @Override
    public ReservationsGetDto updateReservation(ObjectId reservationId, CreateReservationArrivaDeparturelDto updateReservation) throws InvalidDateReservationException, RoomExistsException, HotelExistsException, ReservationExistsException {
        Reservations reservation = reservationRepository.findById(reservationId);
        List<Reservations> reservations = checkRoomReservations(reservation.getHotelN(), reservation.getRoomNumber());
        Reservations reservationUpdate = ReservationConverter.dtoToArrivalDeparture(updateReservation);
        for (Reservations existingReservation : reservations) {
            LocalDate existingArrival = existingReservation.getArrival();
            LocalDate existingDeparture = existingReservation.getDeparture();
            boolean isOverlap =
                    !(updateReservation.departure().isBefore(existingArrival) ||
                            updateReservation.arrival().isAfter(existingDeparture));

            if (isOverlap) {
                throw new ReservationExistsException(MessagesExceptions.RESERVATIONEXISTS);
            }
        }
        reservation.setArrival(updateReservation.arrival());
        reservation.setDeparture(updateReservation.departure());
        reservationRepository.update(reservation);
        return reservationsToDto(reservation);
    }

    @Override
    public ReservationsGetDto findReservationById(ObjectId id) {
        Reservations reservations = reservationRepository.findById(id);
        return reservationsToDto(reservations);
    }

    @Override
    public List<Reservations> checkReservations() {
        List<Reservations> reservations = reservationRepository.listAll();
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
    public List<Reservations> checkRoomReservations(String hotelN, int roomNumber) throws HotelExistsException, RoomExistsException {
        checkHotelRoom(hotelN, roomNumber);
        List<Reservations> reservations = checkReservations().stream()
                .filter(reservation -> reservation.getHotelN().equals(hotelN) && reservation.getRoomNumber() == roomNumber)
                .toList();
        return reservations == null ? Collections.emptyList() : reservations;
    }

    @Override
    public void hasOverlappingReservation(String hotelN, int roomNumber, LocalDate arrival, LocalDate departure) throws RoomExistsException, HotelExistsException, ReservationExistsException {
        List<Reservations> reservations = checkRoomReservations(hotelN, roomNumber);
        for (Reservations existingReservation : reservations) {
            LocalDate existingArrival = existingReservation.getArrival();
            LocalDate existingDeparture = existingReservation.getDeparture();

            boolean isOverlap =
                    arrival.isBefore(existingDeparture) ||
                            departure.isAfter(existingArrival) ||
                            arrival.isEqual(existingDeparture) ||
                            departure.isEqual(existingArrival);

            if (isOverlap) {
                throw new ReservationExistsException(MessagesExceptions.RESERVATIONEXISTS);
            }
        }
    }


    @Override
    public List<ReservationsMultiGetDto> findAllReservations(int page) {
        List<ReservationsMulti> reservationsMultis = reservationsMultiRepository.listAll().page(page, PAGE_SIZE).list();
        return reservationsMultiToDtoList(reservationsMultis);
    }

    public void overlapingChecker(List<ReservationsMulti> reservations, LocalDate arrival, LocalDate departure) throws ReservationExistsException {
        for (ReservationsMulti existingReservation : reservations) {
            LocalDate existingArrival = existingReservation.getArrival();
            LocalDate existingDeparture = existingReservation.getDeparture();

            boolean isOverlap =
                    arrival.isBefore(existingDeparture) ||
                            departure.isAfter(existingArrival) ||
                            arrival.isEqual(existingDeparture) ||
                            departure.isEqual(existingArrival);
            if (isOverlap) {
                throw new ReservationExistsException(MessagesExceptions.RESERVATIONEXISTS);
            }
        }
    }

    public ReservationsMulti addReservation(String hotelN, CreateReservationMultiDto reservation)
            throws HotelExistsException, RoomExistsException, ReservationExistsException, InvalidDateReservationException {

        Hotel hotel = hotelRepository.findByHotelN(hotelN)
                .orElseThrow(() -> new HotelExistsException(MessagesExceptions.HOTELERROR));

        List<CreateRoomsReservationDto> roomReservations = reservation.roomReservations();

        List<RoomReservations> reservations = new ArrayList<>();

        for (CreateRoomsReservationDto roomDto : roomReservations) {
            Rooms room = hotel.getRooms().stream()
                    .filter(r -> r.getRoomType().equals(roomDto.roomType()))
                    .findFirst()
                    .orElseThrow(() -> new RoomExistsException(MessagesExceptions.ROOMERROR));

            hasOverlappingReservation(hotelN, room.getRoomNumber(), reservation.arrival(), reservation.departure());

            RoomReservations roomReservation = new RoomReservations();
            roomReservation.setRoomNumber(room.getRoomNumber());
            roomReservation.setRoomType(roomDto.roomType());
            reservations.add(roomReservation);
        }

        ReservationsMulti reservationUpdate = ReservationConverter.dtoToReservations(reservation);
        reservationUpdate.setHotelN(hotelN);
        reservationUpdate.setArrival(reservation.arrival());
        reservationUpdate.setDeparture(reservation.departure());
        reservationUpdate.setRoomReservations(reservations);

        reservationsMultiRepository.persist(reservationUpdate);

        return reservationUpdate;
    }


}


