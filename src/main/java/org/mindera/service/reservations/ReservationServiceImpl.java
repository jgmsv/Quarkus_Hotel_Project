package org.mindera.service.reservations;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.types.ObjectId;
import org.mindera.converter.reservations.ReservationConverter;
import org.mindera.dto.reservations.CreateReservationArrivaDeparturelDto;
import org.mindera.dto.reservations.CreateReservationDto;
import org.mindera.dto.reservations.ReservationsGetDto;
import org.mindera.model.hotel.Hotel;
import org.mindera.model.hotel.RoomType;
import org.mindera.model.hotel.Rooms;
import org.mindera.model.reservations.Reservations;
import org.mindera.repository.HotelRepository;
import org.mindera.repository.ReservationRepository;
import org.mindera.util.exceptions.hotel.HotelExistsException;
import org.mindera.util.exceptions.reservations.InvalidDateReservationException;
import org.mindera.util.exceptions.reservations.ReservationExistsException;
import org.mindera.util.exceptions.room.RoomExistsException;
import org.mindera.util.messages.MessagesExceptions;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.mindera.converter.reservations.ReservationConverter.reservationsToDto;
import static org.mindera.converter.reservations.ReservationConverter.reservationsToDtoList;

@ApplicationScoped
public class ReservationServiceImpl implements ReservationService {

    @Inject
    HotelRepository hotelRepository;
    @Inject
    ReservationRepository reservationRepository;

    @Override
    public Reservations addReservationToRoom(String hotelN, RoomType roomType, CreateReservationDto reservation) throws HotelExistsException, RoomExistsException, ReservationExistsException, InvalidDateReservationException {
        Hotel hotel = hotelRepository.findByHotelN(hotelN).orElseThrow(() -> new HotelExistsException(MessagesExceptions.HOTELERROR));
        Rooms room = hotel.getRooms().stream().filter(rooms -> rooms.getRoomType().equals(roomType)).findFirst().orElseThrow(() -> new RoomExistsException(MessagesExceptions.ROOMERROR));
        //Rooms room = hotel.getRooms().stream().filter(rooms -> rooms.getRoomNumber() == roomNumber).findFirst().orElseThrow(() -> new RoomExistsException(MessagesExceptions.ROOMERROR));
        Reservations reservationUpdate = ReservationConverter.dtoToReservations(reservation);
        hasOverlappingReservation(hotelN, room.getRoomNumber(), reservation.arrival(), reservation.departure());
        reservationUpdate.setArrival(reservation.arrival());
        reservationUpdate.setDeparture(reservation.departure());
        reservationUpdate.setHotelN(hotelN);
        reservationUpdate.setRoomType(roomType);
        reservationUpdate.setRoomNumber(room.getRoomNumber());
        if (reservationUpdate.getArrival().isBefore(LocalDate.now())) {
            throw new InvalidDateReservationException(MessagesExceptions.INVALIDDATE);
        }
        reservationRepository.persist(reservationUpdate);
        return reservationUpdate;

    }

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
    public List<ReservationsGetDto> findAllReservations() {
        List<Reservations> reservations = reservationRepository.listAll();
        return reservationsToDtoList(reservations);
    }

    @Override
    public ReservationsGetDto findReservationById(ObjectId id) {
        Reservations reservations = reservationRepository.findById(id);
        return reservationsToDto(reservations);
    }


    public List<Reservations> checkReservations() {
        List<Reservations> reservations = reservationRepository.listAll();
        if (reservations.isEmpty()) {
            return reservations.stream().toList();
        }
        return reservations;
    }

    public void checkHotelRoom(String hotelN, int roomNumber) throws RoomExistsException, HotelExistsException {
        Hotel hotel = hotelRepository.findByHotelN(hotelN).orElseThrow(() -> new HotelExistsException(MessagesExceptions.HOTELERROR));
        Rooms room = hotel.getRooms().stream().filter(rooms -> rooms.getRoomNumber() == roomNumber).findFirst().orElseThrow(() -> new RoomExistsException(MessagesExceptions.ROOMERROR));
    }

    public List<Reservations> checkRoomReservations(String hotelN, int roomNumber) throws HotelExistsException, RoomExistsException {
        checkHotelRoom(hotelN, roomNumber);
        List<Reservations> reservations = checkReservations().stream()
                .filter(reservation -> reservation.getHotelN().equals(hotelN) && reservation.getRoomNumber() == roomNumber)
                .toList();
        return reservations == null ? Collections.emptyList() : reservations;
    }

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

}

/*    @Override
    public Reservations addReservationToRoom(String hotelN, int roomNumber, CreateReservationDto reservation) throws HotelExistsException, RoomExistsException, ReservationExistsException, InvalidDateReservationException {
        Hotel hotel = hotelRepository.findByHotelN(hotelN).orElseThrow(() -> new HotelExistsException(MessagesExceptions.HOTELERROR));
        Rooms room = hotel.getRooms().stream().filter(rooms -> rooms.getRoomNumber() == roomNumber).findFirst().orElseThrow(() -> new RoomExistsException(MessagesExceptions.ROOMERROR));
        Reservations reservationUpdate = ReservationConverter.dtoToReservations(reservation);
        hasOverlappingReservation(hotelN, roomNumber, reservation.arrival(), reservation.departure());
        reservationUpdate.setArrival(reservation.arrival());
        reservationUpdate.setDeparture(reservation.departure());
        reservationUpdate.setHotelN(hotelN);
        reservationUpdate.setRoomNumber(roomNumber);
        if (reservationUpdate.getArrival().isBefore(LocalDate.now())) {
            throw new InvalidDateReservationException(MessagesExceptions.INVALIDDATE);
        }
        reservationRepository.persist(reservationUpdate);
        return reservationUpdate;

    }*/




