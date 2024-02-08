package org.mindera.dto.hotel;

import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.mindera.model.hotel.Facilities;
import org.mindera.model.hotel.Rooms;

import java.util.Set;

public record HotelGetDto(
        @Schema(description = "Hotel name", example = "HotelTest")
        String hotelN,
        @Schema(description = "Hotel location", example = "Sample Address")
        String location,
        @Schema(description = "Hotel phone number", example = "912345671")
        String phoneNumber,
        @Schema(description = "Hotel rooms composed by room number, number of beds, room type and room price", example = "room number: 101, number of beds: 1, room type: SINGLEROOM, room price: 100")
        Set<Rooms> rooms,
        @Schema(description = "Hotel facilities", example = "BAR")
        Set<Facilities> facilities
) {

}
