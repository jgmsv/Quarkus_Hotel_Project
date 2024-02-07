package org.mindera.dto.reservations;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;
import java.util.List;

public record CreateReservationMultiDto(
        @FutureOrPresent
        @NotNull
        LocalDate arrival,
        @Future
        @NotNull
        LocalDate departure,
        @NotNull
        String hotelN,
        @NotNull
        String fullName,
        @NotNull
        @Pattern(regexp = "(9[1236][0-9])([0-9]{3})([0-9]{3})")
        String phoneNumber,
        @NotNull
        @Pattern(regexp = " ^[125689]\\d{8}$")
        String vat,
        @NotNull
        List<CreateRoomsReservationDto> roomReservations
) {

}
