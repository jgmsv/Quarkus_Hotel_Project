package org.mindera.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;


public record CreateReservationDto(
        @NotNull
        boolean available,
        LocalDate checkInDate,
        LocalDate checkOutDate
) {

}
