package org.mindera.dto.hotel;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.mindera.model.hotel.Facilities;

import java.util.Set;

public record CreateHotelDto(

        @NotNull
        @Pattern(regexp = "^[a-zA-Z0-9_-]+$")
        @Schema(description = "Hotel name", example = "HotelTest")
        String hotelN,
        @NotNull
        @Schema(description = "Hotel location", example = "Sample Address")
        String location,
        @NotNull
        @Pattern(regexp = "(9[1236][0-9])([0-9]{3})([0-9]{3})")
        @Schema(description = "Hotel phone number", example = "912345671")
        String phoneNumber,
        @NotNull
        @Schema(description = "Hotel rooms composed by room number, number of beds, room type and room price", example = "room number: 101, number of beds: 1, room type: SINGLEROOM, room price: 100")
        Set<CreateRoomDto> rooms,
        @NotNull
        @Schema(description = "Hotel facilities", example = "BAR")
        Set<Facilities> facilities
) {


}
