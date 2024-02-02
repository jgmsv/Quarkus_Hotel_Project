package org.mindera.converter.reservations;

import org.mindera.dto.reservations.CreateReservationArrivalDto;
import org.mindera.dto.reservations.CreateReservationDepartureDto;
import org.mindera.dto.reservations.CreateReservationDto;
import org.mindera.dto.reservations.ReservationsGetDto;
import org.mindera.model.reservations.Reservations;

import java.util.List;

public class ReservationConverter {

    public static Reservations dtoToReservations(CreateReservationDto createReservationDto) {
        return Reservations.builder()
                .firstName(createReservationDto.firstName())
                .lastName(createReservationDto.lastName())
                .phoneNumber(createReservationDto.phoneNumber())
                .vat(createReservationDto.vat())
                .build();
    }

    public static Reservations dtoToArrival(CreateReservationArrivalDto createReservationDto) {
        return Reservations.builder()
                .departure(createReservationDto.arrival())
                .build();
    }

    public static Reservations dtoToDeparture(CreateReservationDepartureDto createReservationDto) {
        return Reservations.builder()
                .departure(createReservationDto.departure())
                .build();
    }

    public static ReservationsGetDto reservationsToDto(Reservations reservations) {
        return new ReservationsGetDto(
                reservations.getArrival(),
                reservations.getDeparture(),
                reservations.getFirstName(),
                reservations.getLastName(),
                reservations.getPhoneNumber(),
                reservations.getVat(),
                reservations.getHotelN(),
                reservations.getRoomNumber(),
                reservations.id
        );
    }


    public static List<ReservationsGetDto> reservationsToDtoList(List<Reservations> reservations) {
        return reservations.stream()
                .map(ReservationConverter::reservationsToDto)
                .toList();
    }
}
