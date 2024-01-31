package org.mindera.dto;

import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record CreateHotelDto(
        @NotNull()
        String hotelN,
        @NotNull
        String location,
        @NotNull
        int phoneNumber,
        @NotNull
        Set<CreateRoomDto> rooms
) {


}
