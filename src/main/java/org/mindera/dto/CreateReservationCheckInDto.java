package org.mindera.dto;

import java.time.LocalDate;

public record CreateReservationCheckInDto(

        boolean available,
        LocalDate checkInDate
) {
}
