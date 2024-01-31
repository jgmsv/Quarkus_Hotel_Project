package org.mindera.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateReservationCheckOutDto(
        @NotNull
        LocalDate checkOutDate
) {
}
