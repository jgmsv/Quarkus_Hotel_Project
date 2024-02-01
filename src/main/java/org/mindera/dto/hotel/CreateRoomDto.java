package org.mindera.dto.hotel;


import jakarta.validation.constraints.NotNull;
import org.mindera.model.hotel.RoomType;


public record CreateRoomDto(
        @NotNull
        int roomNumber,
        @NotNull
        int numberOfBeds,
        @NotNull
        RoomType roomType,
        @NotNull
        int roomPrice,
        @NotNull
        boolean available
) {


}
