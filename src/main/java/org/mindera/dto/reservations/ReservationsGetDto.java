package org.mindera.dto.reservations;

import org.bson.types.ObjectId;

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
        ObjectId id

) {
}
