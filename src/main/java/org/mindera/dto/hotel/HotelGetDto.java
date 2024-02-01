package org.mindera.dto.hotel;

import org.mindera.model.hotel.Rooms;

import java.util.Set;

public record HotelGetDto(
        String hotelN,
        String location,
        int phoneNumber,
        Set<Rooms> rooms
) {

}
