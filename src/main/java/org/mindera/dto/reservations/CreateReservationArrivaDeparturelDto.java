package org.mindera.dto.reservations;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.LocalDate;

public record CreateReservationArrivaDeparturelDto(
        @NotNull
        @FutureOrPresent
        @Schema(description = "Arrival date", example = "2021-12-01")
        LocalDate arrival,
        @NotNull
        @Future
        @Schema(description = "Departure date", example = "2021-12-02")
        LocalDate departure
) {
}
