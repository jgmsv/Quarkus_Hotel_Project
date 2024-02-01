package org.mindera.converter.reservations;

import org.mindera.dto.reservations.CreateReservationCheckOutDto;
import org.mindera.dto.reservations.CreateReservationDto;
import org.mindera.dto.reservations.ReservationsGetDto;
import org.mindera.model.reservations.Reservations;

import java.util.List;

public class ReservationConverter {

    public static Reservations dtoToReservations(CreateReservationDto createReservationDto) {
        return Reservations.builder()
                .checkInDate(createReservationDto.checkInDate())
                .firstName(createReservationDto.firstName())
                .lastName(createReservationDto.lastName())
                .phoneNumber(createReservationDto.phoneNumber())
                .vat(createReservationDto.vat())
                .hotelN(createReservationDto.hotelN())
                .roomNumber(createReservationDto.roomNumber())
                .build();
    }

    public static Reservations dtoToCheckOutReservation(CreateReservationCheckOutDto createReservationDto) {
        return Reservations.builder()
                .checkOutDate(createReservationDto.checkOutDate())
                .build();
    }

    public static ReservationsGetDto reservationsToDto(Reservations reservations) {
        return new ReservationsGetDto(
                reservations.getCheckInDate(),
                reservations.getCheckOutDate(),
                reservations.getFirstName(),
                reservations.getLastName(),
                reservations.getPhoneNumber(),
                reservations.getVat(),
                reservations.getHotelN(),
                reservations.getRoomNumber()
        );
    }

    public static List<ReservationsGetDto> reservationsToDtoList(List<Reservations> reservations) {
        return reservations.stream()
                .map(ReservationConverter::reservationsToDto)
                .toList();
    }
}
