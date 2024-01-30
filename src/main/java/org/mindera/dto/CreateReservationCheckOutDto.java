package org.mindera.dto;

import java.time.LocalDate;

public record CreateReservationCheckOutDto(
        LocalDate checkOutDate
) {
}
