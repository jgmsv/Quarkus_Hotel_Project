package org.mindera.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.PathParam;
import org.mindera.converter.ReservationConverter;
import org.mindera.dto.CreateHotelDto;
import org.mindera.dto.CreateReservationCheckInDto;
import org.mindera.dto.CreateReservationCheckOutDto;
import org.mindera.dto.HotelGetDto;
import org.mindera.model.Hotel;
import org.mindera.model.Reservations;
import org.mindera.model.Rooms;
import org.mindera.repository.HotelRepository;
import org.mindera.util.exceptions.HotelDuplication;
import org.mindera.util.exceptions.HotelException;
import org.mindera.util.exceptions.RoomException;
import org.mindera.util.messages.Messages;

import java.util.List;
import java.util.Optional;

import static org.mindera.converter.HotelConverter.*;

@ApplicationScoped
public class HotelServiceImpl implements HotelService {

    @Inject
    HotelRepository hotelRepository;

    @Override
    public Hotel addHotel(CreateHotelDto createHotelDto) throws HotelException, HotelDuplication {
        Hotel hotel = dtoToHotel(createHotelDto);
        hotel.saveWithUniqueCheck(); //hotelRepository.persist(hotel);
        return hotel;
    }

    @Override
    public List<HotelGetDto> findAllHotels() {
        List<Hotel> hotels = hotelRepository.listAll();
        return hotelToDtoList(hotels);
    }

    @Override
    public HotelGetDto updateRoomCheckInDate(String hotelN, int roomNumber, CreateReservationCheckInDto reservations) throws HotelException, RoomException {
        Hotel hotel = hotelRepository.findByHotelN(hotelN).orElseThrow(() -> new HotelException(Messages.HOTELERROR));
        Rooms roomToUpdate = hotel.getRooms().stream().filter(rooms -> rooms.getRoomNumber() == roomNumber).findFirst().orElseThrow(() -> new RoomException(Messages.ROOMERROR));
        Reservations reservationUpdate = ReservationConverter.dtoToCheckInReservation(reservations);
        roomToUpdate.setReservations(reservationUpdate);
        hotelRepository.persist(hotel);
        return hotelToDto(hotel);
    }

    @Override
    public HotelGetDto updateRoomCheckOutDate(String hotelN, int roomNumber, CreateReservationCheckOutDto reservations) throws HotelException, RoomException {
        Hotel hotel = hotelRepository.findByHotelN(hotelN).orElseThrow(() -> new HotelException(Messages.HOTELERROR));
        Rooms roomToUpdate = hotel.getRooms().stream().filter(rooms -> rooms.getRoomNumber() == roomNumber).findFirst().orElseThrow(() -> new RoomException(Messages.ROOMERROR));
        Reservations reservationUpdate = ReservationConverter.dtoToCheckOutReservation(reservations);
        roomToUpdate.setReservations(reservationUpdate);
        hotelRepository.persist(hotel);
        return hotelToDto(hotel);
    }

    @Override
    public HotelGetDto updateRoomPrice(String hotelN, int roomNumber, int price) throws HotelException, RoomException {
        Hotel hotel = hotelRepository.findByHotelN(hotelN).orElseThrow(() -> new HotelException(Messages.HOTELERROR));
        Rooms roomToUpdate = hotel.getRooms().stream().filter(rooms -> rooms.getRoomNumber() == roomNumber).findFirst().orElseThrow(() -> new RoomException(Messages.ROOMERROR));
        roomToUpdate.setRoomPrice(price);
        hotelRepository.persist(hotel);
        return hotelToDto(hotel);
    }

    @Override
    public HotelGetDto findHotelByHotelN(String hotelN) throws HotelException {
        Hotel hotel = hotelRepository.findByHotelN(hotelN).orElseThrow(() -> new HotelException(Messages.HOTELERROR));
        return hotelToDto(hotel);
    }


    public Optional<Hotel> findByHotelN(@PathParam("hotelN") String hotelN) {
        return hotelRepository.findByHotelN(hotelN);
    }
}
