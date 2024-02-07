package org.mindera.service.hotel;

import org.mindera.dto.hotel.CreateHotelDto;
import org.mindera.dto.hotel.FacilitiesDto;
import org.mindera.dto.hotel.HotelGetDto;
import org.mindera.model.hotel.Hotel;
import org.mindera.util.exceptions.hotel.HotelAdressException;
import org.mindera.util.exceptions.hotel.HotelDuplicationException;
import org.mindera.util.exceptions.hotel.HotelExistsException;
import org.mindera.util.exceptions.hotel.HotelFacilitiesException;
import org.mindera.util.exceptions.room.RoomExistsException;
import org.mindera.util.exceptions.room.RoomPriceException;

import java.util.List;

public interface HotelService {


    Hotel addHotel(CreateHotelDto createHotelDto) throws HotelExistsException, HotelDuplicationException;

    List<HotelGetDto> findAllHotels(int page);

    HotelGetDto findHotelByHotelN(String hotelN) throws HotelExistsException;

    HotelGetDto updateRoomPrice(String hotelN, int roomNumber, int price) throws HotelExistsException, RoomExistsException, RoomPriceException;

    List<HotelGetDto> findHotelsByFacilities(List<FacilitiesDto> facilities) throws HotelAdressException, HotelFacilitiesException;

    List<HotelGetDto> findHotelsByAddress(String location) throws HotelAdressException;
}
