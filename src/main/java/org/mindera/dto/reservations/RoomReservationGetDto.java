package org.mindera.dto.reservations;

import org.mindera.model.hotel.RoomType;

public record RoomReservationGetDto(
        RoomType roomType,
        int roomNumber) {
}
