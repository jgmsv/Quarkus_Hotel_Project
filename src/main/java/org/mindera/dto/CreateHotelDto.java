package org.mindera.dto;

import java.util.Set;

public record CreateHotelDto(
        String hotelN,
        String location,
        int phoneNumber,
        Set<CreateRoomDto> rooms
) {


}
