package org.mindera.service.hotel;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.mindera.dto.hotel.CreateHotelDto;
import org.mindera.dto.hotel.FacilitiesDto;
import org.mindera.dto.hotel.HotelGetDto;
import org.mindera.model.hotel.Facilities;
import org.mindera.model.hotel.Hotel;
import org.mindera.model.hotel.Rooms;
import org.mindera.repository.HotelRepository;
import org.mindera.util.exceptions.hotel.HotelAdressException;
import org.mindera.util.exceptions.hotel.HotelDuplicationException;
import org.mindera.util.exceptions.hotel.HotelExistsException;
import org.mindera.util.exceptions.hotel.HotelFacilitiesException;
import org.mindera.util.exceptions.room.RoomExistsException;
import org.mindera.util.exceptions.room.RoomPriceException;
import org.mindera.util.messages.MessagesExceptions;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mindera.converter.hotel.HotelConverter.*;

@ApplicationScoped
public class HotelServiceImpl implements HotelService {
    private final int PAGE_SIZE = 10;

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
    public List<HotelGetDto> findAllHotels(int page) {
        List<Hotel> hotels = hotelRepository.findAll().page(0, PAGE_SIZE).list();
        return hotelToDtoList(hotels);
    }

    @Override
    public List<HotelGetDto> findHotelsByFacilities(List<FacilitiesDto> facilities) throws HotelFacilitiesException {
        List<String> facilitiesString = facilities.stream().map(FacilitiesDto::facilities).toList();
        List<Facilities> facilitiesList = Arrays.stream(Facilities.values()).filter(facilities1 -> facilitiesString.contains(facilities1.name())).toList(
        );
        List<Hotel> hotel = hotelRepository.findByFacilities(facilitiesList);
        if (hotel.isEmpty()) {
            throw new HotelFacilitiesException(MessagesExceptions.HOTELFACILITIESNOTFOUND);
        }
        return hotelToDtoList(hotel);
    }

    @Override
    public List<HotelGetDto> findHotelsByAddress(String location) throws HotelAdressException {
        List<Hotel> hotel = hotelRepository.findByAddress(location);

        if (hotel.isEmpty()) {
            throw new HotelAdressException(MessagesExceptions.HOTELADDRESSNOTFOUND);
        }
        ;
        return hotelToDtoList(hotel);
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
