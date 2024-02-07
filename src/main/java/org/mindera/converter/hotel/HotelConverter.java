package org.mindera.converter.hotel;

import org.mindera.dto.hotel.CreateHotelDto;
import org.mindera.dto.hotel.HotelGetDto;
import org.mindera.model.hotel.Hotel;

import java.util.List;

public class HotelConverter {

    public static Hotel dtoToHotel(CreateHotelDto createHotelDto) {
        return Hotel.builder()
                .hotelN(createHotelDto.hotelN())
                .location(createHotelDto.location())
                .phoneNumber(createHotelDto.phoneNumber())
                .rooms(RoomsConverter.dtoToRoomsSet(createHotelDto.rooms()))
                .facilities(createHotelDto.facilities())
                .build();
    }

    public static HotelGetDto hotelToDto(Hotel hotel) {
        return new HotelGetDto(
                hotel.getHotelN(),
                hotel.getLocation(),
                hotel.getPhoneNumber(),
                hotel.getRooms(),
                hotel.getFacilities()

        );
    }

    public static List<HotelGetDto> hotelToDtoList(List<Hotel> hotels) {
        return hotels.stream()
                .map(HotelConverter::hotelToDto)
                .toList();
    }


}
