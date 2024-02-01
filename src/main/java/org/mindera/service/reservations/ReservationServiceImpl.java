package org.mindera.service.reservations;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.types.ObjectId;
import org.mindera.converter.reservations.ReservationConverter;
import org.mindera.dto.reservations.CreateReservationCheckOutDto;
import org.mindera.dto.reservations.CreateReservationDto;
import org.mindera.dto.reservations.ReservationsGetDto;
import org.mindera.model.hotel.Hotel;
import org.mindera.model.hotel.Rooms;
import org.mindera.model.reservations.Reservations;
import org.mindera.repository.HotelRepository;
import org.mindera.repository.ReservationRepository;
import org.mindera.util.exceptions.hotel.HotelExistsException;
import org.mindera.util.exceptions.reservations.InvalidDateReservationException;
import org.mindera.util.exceptions.room.RoomExistsException;
import org.mindera.util.messages.MessagesExceptions;

import java.time.LocalDate;
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
    public Reservations addReservationToRoom(String hotelN, int roomNumber, CreateReservationDto reservation) throws HotelExistsException, RoomExistsException, InvalidDateReservationException {
        Hotel hotel = hotelRepository.findByHotelN(hotelN).orElseThrow(() -> new HotelExistsException(MessagesExceptions.HOTELERROR));
        Rooms room = hotel.getRooms().stream().filter(rooms -> rooms.getRoomNumber() == roomNumber).findFirst().orElseThrow(() -> new RoomExistsException(MessagesExceptions.ROOMERROR));
        Reservations reservationUpdate = ReservationConverter.dtoToReservations(reservation);
        reservationUpdate.setHotelN(hotelN);
        reservationUpdate.setRoomNumber(roomNumber);
        if (reservationUpdate.getCheckInDate().isBefore(LocalDate.now())) {
            throw new InvalidDateReservationException(MessagesExceptions.INVALIDDATE);
        }
        reservationRepository.persist(reservationUpdate);
        room.setAvailable(false);

        return reservationUpdate;

    }

    @Override
    public ReservationsGetDto updateCheckOutDate(String hotelN, int roomNumber, CreateReservationCheckOutDto reservation, ObjectId reservationId) throws HotelExistsException, RoomExistsException, InvalidDateReservationException {
        Hotel hotel = hotelRepository.findByHotelN(hotelN).orElseThrow(() -> new HotelExistsException(MessagesExceptions.HOTELERROR));
        Rooms room = hotel.getRooms().stream().filter(rooms -> rooms.getRoomNumber() == roomNumber).findFirst().orElseThrow(() -> new RoomExistsException(MessagesExceptions.ROOMERROR));
        Reservations reservations = reservationRepository.findById(reservationId); // .orElseThrow(() -> new InvalidReservationId(MessagesExceptions.INVALIDRESERVATIONID));
        Reservations reservationUpdate = ReservationConverter.dtoToCheckOutReservation(reservation);
        reservations.setCheckOutDate(reservationUpdate.getCheckOutDate());
        if (reservationUpdate.getCheckOutDate().isBefore(LocalDate.now())) {
            throw new InvalidDateReservationException(MessagesExceptions.INVALIDDATE);
        }
        reservationRepository.persist(reservationUpdate);
        room.setAvailable(true);
        return null;
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
}
