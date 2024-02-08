package org.mindera.dto.reservations;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;

public record CreateReservationMultiDto(
        @FutureOrPresent
        @NotNull
        @Schema(description = "Arrival date", example = "2021-12-01")
        LocalDate arrival,
        @Future
        @NotNull
        @Schema(description = "Departure date", example = "2021-12-02")
        LocalDate departure,
        @NotNull
        @Pattern(regexp = "^[a-zA-Z0-9_-]+$")
        @Schema(description = "Hotel name", example = "HotelTest")
        String hotelN,
        @NotNull
        @Schema(description = "Full name", example = "John Doe")
        String fullName,
        @NotNull
        @Pattern(regexp = "(9[1236][0-9])([0-9]{3})([0-9]{3})")
        @Schema(description = "Phone number", example = "912345671")
        String phoneNumber,
        @NotNull
        @Pattern(regexp = " ^[125689]\\d{8}$")
        @Schema(description = "VAT", example = "123456789")
        String vat,
        @NotNull
        @Schema(description = "Rooms types for reservations", example = "room type: SINGLEROOM")
        List<CreateRoomsReservationDto> roomReservations
) {

}
