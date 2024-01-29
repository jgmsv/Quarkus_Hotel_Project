package org.mindera.converter;

import org.mindera.dto.CreateReservationDto;
import org.mindera.model.Reservations;

public class ReservationConverter {

    public static Reservations dtoToReservations(CreateReservationDto createReservationDto) {
        return Reservations.builder()
                .available(createReservationDto.available())
                .checkInDate(createReservationDto.checkInDate())
                .checkOutDate(createReservationDto.checkOutDate())
                .build();
    }

}
