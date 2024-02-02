package org.mindera.dto.reservations;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateReservationDepartureDto(
        @NotNull
        LocalDate departure
) {
}
