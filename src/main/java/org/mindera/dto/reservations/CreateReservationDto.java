package org.mindera.dto.reservations;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;


public record CreateReservationDto(
        @NotNull
        LocalDate checkInDate,
        @NotNull
        String firstName,
        @NotNull
        String lastName,
        @NotNull
        int phoneNumber,
        @NotNull
        int vat,
        @NotNull
        String hotelN,
        @NotNull
        int roomNumber
) {

}
