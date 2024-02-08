package org.mindera.dto.hotel;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.mindera.model.hotel.Facilities;

import java.util.Set;

public record CreateHotelDto(

        @NotNull
        @Pattern(regexp = "^[a-zA-Z0-9_-]+$")
        String hotelN,
        @NotNull
        String location,
        @NotNull
        @Pattern(regexp = "(9[1236][0-9])([0-9]{3})([0-9]{3})")
        String phoneNumber,
        @NotNull
        Set<CreateRoomDto> rooms,
        @NotNull
        Set<Facilities> facilities
) {


}
