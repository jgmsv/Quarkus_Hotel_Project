package org.mindera.dto.reservations;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateReservationArrivalDto(
        @NotNull
        LocalDate arrival
) {
}
