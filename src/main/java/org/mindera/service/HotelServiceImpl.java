package org.mindera.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.mindera.converter.ReservationConverter;
import org.mindera.dto.CreateHotelDto;
import org.mindera.dto.CreateReservationCheckInDto;
import org.mindera.dto.CreateReservationCheckOutDto;
import org.mindera.dto.HotelGetDto;
import org.mindera.model.Hotel;
import org.mindera.model.Reservations;
import org.mindera.model.Rooms;
import org.mindera.repository.HotelRepository;
import org.mindera.util.exceptions.hotel.HotelDuplicationException;
import org.mindera.util.exceptions.hotel.HotelExistsException;
import org.mindera.util.exceptions.reservations.InvalidDateReservation;
import org.mindera.util.exceptions.room.RoomExistsException;
import org.mindera.util.exceptions.room.RoomPriceException;
import org.mindera.util.messages.MessagesExceptions;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mindera.converter.HotelConverter.*;

@ApplicationScoped
public class HotelServiceImpl implements HotelService {

    @Inject
    HotelRepository hotelRepository;

    @Override
    public Hotel addHotel(CreateHotelDto createHotelDto) throws HotelDuplicationException {
        Hotel hotel = dtoToHotel(createHotelDto);
        if (hotelRepository.isHotelNUnique(hotel.getHotelN())) {
            hotelRepository.persist(hotel);
        } else {
            throw new HotelDuplicationException(MessagesExceptions.DUPLICATEDHOTEL);
        }
        return hotel;
    }

    @Override
    public List<HotelGetDto> findAllHotels() {
        List<Hotel> hotels = hotelRepository.listAll();
        return hotelToDtoList(hotels);
    }

    @Override
    public HotelGetDto updateRoomCheckInDate(String hotelN, int roomNumber, CreateReservationCheckInDto reservations) throws HotelExistsException, RoomExistsException, InvalidDateReservation {
        Hotel hotel = hotelRepository.findByHotelN(hotelN).orElseThrow(() -> new HotelExistsException(MessagesExceptions.HOTELERROR));
        Rooms roomToUpdate = hotel.getRooms().stream().filter(rooms -> rooms.getRoomNumber() == roomNumber).findFirst().orElseThrow(() -> new RoomExistsException(MessagesExceptions.ROOMERROR));
        if (reservations.checkInDate().isBefore(LocalDate.now())) {
            throw new InvalidDateReservation(MessagesExceptions.INVALIDDATE);
        }
        Reservations reservationUpdate = ReservationConverter.dtoToCheckInReservation(reservations);
        roomToUpdate.setReservations(reservationUpdate);
        hotelRepository.update(hotel);
        return hotelToDto(hotel);
    }

    @Override
    public HotelGetDto updateRoomCheckOutDate(String hotelN, int roomNumber, CreateReservationCheckOutDto reservations) throws HotelExistsException, RoomExistsException, InvalidDateReservation {
        Hotel hotel = hotelRepository.findByHotelN(hotelN).orElseThrow(() -> new HotelExistsException(MessagesExceptions.HOTELERROR));
        Rooms roomToUpdate = hotel.getRooms().stream().filter(rooms -> rooms.getRoomNumber() == roomNumber).findFirst().orElseThrow(() -> new RoomExistsException(MessagesExceptions.ROOMERROR));
        if (reservations.checkOutDate().isAfter(LocalDate.now())) {
            throw new InvalidDateReservation(MessagesExceptions.INVALIDDATE);
        }
        Reservations reservationUpdate = ReservationConverter.dtoToCheckOutReservation(reservations);
        roomToUpdate.setReservations(reservationUpdate);
        reservationUpdate.setAvailable(true); //cache parar guardar
        hotelRepository.update(hotel);
        return hotelToDto(hotel);
    }

    @Override
    public HotelGetDto updateRoomPrice(String hotelN, int roomNumber, int price) throws HotelExistsException, RoomExistsException, RoomPriceException {
        Hotel hotel = hotelRepository.findByHotelN(hotelN).orElseThrow(() -> new HotelExistsException(MessagesExceptions.HOTELERROR));
        Rooms roomToUpdate = hotel.getRooms().stream().filter(rooms -> rooms.getRoomNumber() == roomNumber).findFirst().orElseThrow(() -> new RoomExistsException(MessagesExceptions.ROOMERROR));
        if (price <= roomToUpdate.getRoomPrice()) {
            throw new RoomPriceException(MessagesExceptions.PRICEERROR);
        }
        roomToUpdate.setRoomPrice(price);
        hotelRepository.update(hotel);
        return hotelToDto(hotel);
    }

    @Override
    public HotelGetDto findHotelByHotelN(String hotelN) throws HotelExistsException {
        try {
            Hotel hotel = hotelRepository.findByHotelN(hotelN).orElseThrow(() -> new HotelExistsException(MessagesExceptions.HOTELERROR));
            return hotelToDto(hotel);
        } catch (HotelExistsException e) {
            throw e;
        }
    }


    public Optional<Hotel> findByHotelN(String hotelN) {
        return hotelRepository.findByHotelN(hotelN);
    }

}
