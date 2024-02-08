package org.mindera.dto.hotel;

import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.mindera.model.hotel.RoomType;

import java.util.List;


public record HotelRoomTypeGetDto(
        @Schema(description = "Hotel name", example = "Hotel A")
        String hotelN,
        @Schema(description = "Hotel room type", example = "SINGLEROOM")
        List<RoomType> hotelRoomType
) {

}
