package org.mindera.converter.hotel;

import org.mindera.dto.hotel.CreateHotelDto;
import org.mindera.dto.hotel.HotelGetDto;
import org.mindera.model.hotel.Hotel;

import java.util.List;

/**
 * The HotelConverter class provides methods to convert between different representations of Hotel objects.
 */
public class HotelConverter {
    /**
     * Converts a CreateHotelDto object to a Hotel object.
     *
     * @param createHotelDto the CreateHotelDto object to be converted
     * @return the converted Hotel object
     */
    public static Hotel dtoToHotel(CreateHotelDto createHotelDto) {
        return Hotel.builder()
                .hotelN(createHotelDto.hotelN())
                .location(createHotelDto.location())
                .phoneNumber(createHotelDto.phoneNumber())
                .rooms(RoomsConverter.dtoToRoomsSet(createHotelDto.rooms()))
                .facilities(createHotelDto.facilities())
                .build();
    }

    /**
     * Converts a Hotel object to a HotelGetDto object.
     *
     * @param hotel the Hotel object to be converted
     * @return the converted HotelGetDto object
     */
    public static HotelGetDto hotelToDto(Hotel hotel) {
        return new HotelGetDto(
                hotel.getHotelN(),
                hotel.getLocation(),
                hotel.getPhoneNumber(),
                hotel.getRooms(),
                hotel.getFacilities()

        );
    }

    /**
     * Converts a list of Hotel objects to a list of HotelGetDto objects.
     *
     * @param hotels the list of Hotel objects to be converted
     * @return the converted list of HotelGetDto objects
     */
    public static List<HotelGetDto> hotelToDtoList(List<Hotel> hotels) {
        return hotels.stream()
                .map(HotelConverter::hotelToDto)
                .toList();
    }


}
