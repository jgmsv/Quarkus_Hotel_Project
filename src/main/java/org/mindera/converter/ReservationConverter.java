package org.mindera.converter;

import org.mindera.dto.CreateReservationCheckInDto;
import org.mindera.dto.CreateReservationCheckOutDto;
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

    public static Reservations dtoToCheckInReservation(CreateReservationCheckInDto createReservationDto) {
        return Reservations.builder()
                .available(createReservationDto.available())
                .checkInDate(createReservationDto.checkInDate())
                .build();
    }

    public static Reservations dtoToCheckOutReservation(CreateReservationCheckOutDto createReservationDto) {
        return Reservations.builder()
                .checkOutDate(createReservationDto.checkOutDate())
                .build();
    }

}
