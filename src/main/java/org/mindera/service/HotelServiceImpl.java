package org.mindera.service;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.PathParam;
import org.mindera.converter.ReservationConverter;
import org.mindera.dto.CreateHotelDto;
import org.mindera.dto.CreateReservationDto;
import org.mindera.dto.HotelGetDto;
import org.mindera.model.Hotel;
import org.mindera.model.Reservations;
import org.mindera.model.Rooms;
import org.mindera.repository.HotelRepository;
import org.mindera.util.exceptions.HotelException;
import org.mindera.util.exceptions.RoomException;
import org.mindera.util.messages.Messages;

import java.util.List;
import java.util.Optional;

import static org.mindera.converter.HotelConverter.*;

@ApplicationScoped
@Transactional
public class HotelServiceImpl implements HotelService {

    @Inject
    HotelRepository hotelRepository;

    @Override
    public Hotel addHotel(CreateHotelDto createHotelDto) throws HotelException {
        Hotel hotel = dtoToHotel(createHotelDto);
        hotelRepository.persist(hotel);
        return hotel;
    }

    @Override
    public List<HotelGetDto> findAllHotels() {
        PanacheQuery<Hotel> hotelsQuery = hotelRepository.findAll();
        List<Hotel> hotels = hotelsQuery.list();
        return hotelToDtoList(hotels);
    }

    @Override
    public HotelGetDto update(@PathParam("hotelN") String hotelN, @PathParam("roomNumber") int roomNumber, CreateReservationDto reservations) throws HotelException, RoomException {
        Hotel hotel = hotelRepository.findByHotelN(hotelN).orElseThrow(()-> new HotelException (Messages.HOTELERROR));
        Rooms roomToUpdate = hotel.getRooms().stream().filter(rooms -> rooms.getRoomNumber() == roomNumber).findFirst().orElseThrow(()-> new RoomException(Messages.ROOMERROR));
        Reservations reservationUpdate = ReservationConverter.dtoToReservations(reservations);
        roomToUpdate.setReservations(reservationUpdate);
        hotelRepository.persist(hotel);
        return hotelToDto(hotel);

    }

    @Override
    public HotelGetDto findHotelByHotelN(String hotelN) throws HotelException {
        Hotel hotel = hotelRepository.findByHotelN(hotelN).orElseThrow(() -> new HotelException(Messages.HOTELERROR));
        return hotelToDto(hotel);
    }

    @Override
    public Optional<Hotel> findByHotelN(@PathParam("hotelN") String hotelN) {
        return hotelRepository.findByHotelN(hotelN);
    }
}
