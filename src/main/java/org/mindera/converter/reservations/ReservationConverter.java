package org.mindera.converter.reservations;

import org.mindera.dto.reservations.*;
import org.mindera.model.reservations.Reservations;
import org.mindera.model.reservations.ReservationsMulti;

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

    //
    public static ReservationsMulti dtoToReservations(CreateReservationMultiDto createReservationMultiDto) {
        return ReservationsMulti.builder()
                .arrival(createReservationMultiDto.arrival())
                .departure(createReservationMultiDto.departure())
                .firstName(createReservationMultiDto.firstName())
                .lastName(createReservationMultiDto.lastName())
                .phoneNumber(createReservationMultiDto.phoneNumber())
                .vat(createReservationMultiDto.vat())
                .hotelN(createReservationMultiDto.hotelN())
                .roomReservations(ReservationRoomsConverter.dtoToRoomsReservationList(createReservationMultiDto.roomReservations()))
                .build();
    }

    public static ReservationsMultiGetDto reservationMultiToDto(ReservationsMulti reservationsMulti) {
        return new ReservationsMultiGetDto(
                reservationsMulti.getArrival(),
                reservationsMulti.getDeparture(),
                reservationsMulti.getHotelN(),
                reservationsMulti.getFirstName(),
                reservationsMulti.getLastName(),
                reservationsMulti.getPhoneNumber(),
                reservationsMulti.getVat(),
                reservationsMulti.getRoomReservations(),
                reservationsMulti.id
        );
    }

    public static List<ReservationsMultiGetDto> reservationsMultiToDtoList(List<ReservationsMulti> reservationsMulti) {
        return reservationsMulti.stream()
                .map(ReservationConverter::reservationMultiToDto)
                .toList();
    }
}

