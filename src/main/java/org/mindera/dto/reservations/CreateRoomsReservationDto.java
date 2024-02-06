package org.mindera.dto.reservations;

import jakarta.validation.constraints.NotNull;
import org.mindera.model.hotel.RoomType;


public record CreateRoomsReservationDto(
        @NotNull
        RoomType roomType
) {
}
