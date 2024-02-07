package org.mindera.dto.hotel;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.Set;

public record CreateHotelDto(
        @NotNull()
        String hotelN,
        @NotNull
        String location,
        @NotNull
        @Pattern(regexp = "(9[1236][0-9])([0-9]{3})([0-9]{3})")
        String phoneNumber,
        @NotNull
        Set<CreateRoomDto> rooms
) {


}
