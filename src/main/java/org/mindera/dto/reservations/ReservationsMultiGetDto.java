package org.mindera.dto.reservations;

import org.bson.types.ObjectId;
import org.mindera.model.reservations.RoomReservations;

import java.time.LocalDate;
import java.util.List;

public record ReservationsMultiGetDto(
        LocalDate arrival,
        LocalDate departure,
        String hotelN,
        String firstName,
        String lastName,
        int phoneNumber,
        int vat,
        List<RoomReservations> roomReservations,
        ObjectId id
) {
}
