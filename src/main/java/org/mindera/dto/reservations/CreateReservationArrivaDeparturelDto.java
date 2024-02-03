package org.mindera.dto.reservations;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateReservationArrivaDeparturelDto(
        @NotNull
        LocalDate arrival,
        @NotNull
        LocalDate departure
) {
}
