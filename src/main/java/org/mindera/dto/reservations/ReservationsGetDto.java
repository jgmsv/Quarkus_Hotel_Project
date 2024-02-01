package org.mindera.dto.reservations;

import java.time.LocalDate;

public record ReservationsGetDto(
        LocalDate checkInDate,
        LocalDate checkOutDate,
        String firstName,
        String lastName,
        int phoneNumber,
        int vat,
        String hotelN,
        int roomNumber
) {
}
