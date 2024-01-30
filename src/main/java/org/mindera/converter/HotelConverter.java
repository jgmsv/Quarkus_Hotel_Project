package org.mindera.converter;

import org.mindera.dto.CreateHotelDto;
import org.mindera.dto.HotelGetDto;
import org.mindera.model.Hotel;

import java.util.List;

public class HotelConverter {

    public static Hotel dtoToHotel(CreateHotelDto createHotelDto) {
        return Hotel.builder()
                .hotelN(createHotelDto.hotelN())
                .location(createHotelDto.location())
                .phoneNumber(createHotelDto.phoneNumber())
                .rooms(RoomsConverter.dtoToRoomsSet(createHotelDto.rooms()))
                .build();
    }

    public static HotelGetDto hotelToDto(Hotel hotel) {
        return new HotelGetDto(
                hotel.getHotelN(),
                hotel.getLocation(),
                hotel.getPhoneNumber(),
                hotel.getRooms()

        );
    }

    public static List<HotelGetDto> hotelToDtoList(List<Hotel> hotels) {
        return hotels.stream()
                .map(HotelConverter::hotelToDto)
                .toList();
    }


}
