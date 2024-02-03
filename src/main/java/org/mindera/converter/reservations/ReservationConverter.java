package org.mindera.converter.reservations;

import org.mindera.dto.reservations.CreateReservationArrivaDeparturelDto;
import org.mindera.dto.reservations.CreateReservationDto;
import org.mindera.dto.reservations.ReservationsGetDto;
import org.mindera.model.reservations.Reservations;

import java.util.List;

public class ReservationConverter {

    public static Reservations dtoToReservations(CreateReservationDto createReservationDto) {
        return Reservations.builder()
                .arrival(createReservationDto.arrival())
                .departure(createReservationDto.departure())
                .firstName(createReservationDto.firstName())
                .lastName(createReservationDto.lastName())
                .phoneNumber(createReservationDto.phoneNumber())
                .vat(createReservationDto.vat())
                .build();
    }

    public static Reservations dtoToArrivalDeparture(CreateReservationArrivaDeparturelDto createReservationDto) {
        return Reservations.builder()
                .arrival(createReservationDto.arrival())
                .departure(createReservationDto.arrival())
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
                reservations.getRoomType(),
                reservations.id
        );
    }


    public static List<ReservationsGetDto> reservationsToDtoList(List<Reservations> reservations) {
        return reservations.stream()
                .map(ReservationConverter::reservationsToDto)
                .toList();
    }
}
