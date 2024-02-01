package org.mindera.dto.reservations;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateReservationCheckOutDto(
        @NotNull
        LocalDate checkOutDate,
        @NotNull
        String hotelN,
        @NotNull
        int roomNumber
) {
}
