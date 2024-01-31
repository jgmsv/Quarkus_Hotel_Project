package org.mindera.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateReservationCheckInDto(
        @NotNull
        boolean available,
        @NotNull
        LocalDate checkInDate
) {
}
