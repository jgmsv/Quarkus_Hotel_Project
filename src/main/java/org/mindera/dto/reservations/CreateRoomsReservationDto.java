package org.mindera.dto.reservations;

import jakarta.validation.constraints.NotNull;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.mindera.model.hotel.RoomType;


public record CreateRoomsReservationDto(
        @NotNull
        @Schema(description = "Rooms types for reservations", example = "room type: SINGLEROOM")
        RoomType roomType
) {
}
