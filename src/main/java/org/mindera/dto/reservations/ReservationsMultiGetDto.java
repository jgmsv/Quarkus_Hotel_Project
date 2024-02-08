package org.mindera.dto.reservations;

import org.bson.types.ObjectId;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.mindera.model.reservations.RoomReservations;

import java.time.LocalDate;
import java.util.List;

public record ReservationsMultiGetDto(
        @Schema(description = "Arrival date", example = "2021-12-01")
        LocalDate arrival,
        @Schema(description = "Departure date", example = "2021-12-02")
        LocalDate departure,
        @Schema(description = "Hotel name", example = "HotelTest")
        String hotelN,
        @Schema(description = "Full name", example = "John Doe")
        String fullName,
        @Schema(description = "Phone number", example = "912345671")
        String phoneNumber,
        @Schema(description = "VAT", example = "123456789")
        String vat,
        @Schema(description = "Rooms types for reservations", example = "room type: SINGLEROOM, room number: 101")
        List<RoomReservations> roomReservations,
        @Schema(description = "Reservation id", example = "5f8b3f9f5f3e0d1f3c3e3e3e")
        ObjectId id
) {
}
