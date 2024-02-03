package org.mindera.dto.reservations;

import org.bson.types.ObjectId;
import org.mindera.model.hotel.RoomType;

import java.time.LocalDate;

public record ReservationsGetDto(
        LocalDate arrival,
        LocalDate departure,
        String firstName,
        String lastName,
        int phoneNumber,
        int vat,
        String hotelN,
        int roomNumber,
        RoomType roomType,
        ObjectId id

) {
}
