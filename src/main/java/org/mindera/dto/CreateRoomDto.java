package org.mindera.dto;


import jakarta.validation.constraints.NotNull;
import org.mindera.model.RoomType;


public record CreateRoomDto(
        @NotNull
        int roomNumber,
        @NotNull
        int numberOfBeds,
        @NotNull
        RoomType roomType,
        @NotNull
        int roomPrice,
        CreateReservationDto reservations
) {


}
