package org.mindera.dto.reservations;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;


public record CreateReservationDto(
        @NotNull
        LocalDate arrival,
        @NotNull
        LocalDate departure,
        @NotNull
        String hotelN,
        @NotNull
        int roomNumber,
        @NotNull
        String firstName,
        @NotNull
        String lastName,
        @NotNull
        int phoneNumber,
        @NotNull
        int vat

) {

}
