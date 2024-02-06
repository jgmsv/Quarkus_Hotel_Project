package org.mindera.dto.reservations;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;
import java.util.List;

public record CreateReservationMultiDto(
        @NotNull
        LocalDate arrival,
        @NotNull
        LocalDate departure,
        @NotNull
        String hotelN,
        @NotNull
        String firstName,
        @NotNull
        String lastName,
        @NotNull
        @Pattern(regexp = "(9[1236][0-9])([0-9]{3})([0-9]{3})")
        int phoneNumber,
        @NotNull
        @Pattern(regexp = " ^[125689]\\d{8}$")
        int vat,
        @NotNull
        List<CreateRoomsReservationDto> roomReservations
) {

}
