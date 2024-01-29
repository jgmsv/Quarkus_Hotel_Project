package org.mindera.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


public record CreateReservationDto (
        boolean available,
        LocalDate checkInDate,
        LocalDate checkOutDate
)
{

}
