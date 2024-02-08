package org.mindera.dto.reservations;

import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.mindera.model.hotel.RoomType;

public record RoomReservationGetDto(
        @Schema(description = "Room type", example = "SINGLEROOM")
        RoomType roomType,
        @Schema(description = "Room number", example = "101")
        int roomNumber) {
}
