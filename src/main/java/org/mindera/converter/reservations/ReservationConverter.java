package org.mindera.converter.reservations;

import org.mindera.dto.reservations.CreateReservationArrivaDeparturelDto;
import org.mindera.dto.reservations.CreateReservationMultiDto;
import org.mindera.dto.reservations.ReservationsMultiGetDto;
import org.mindera.model.reservations.ReservationsMulti;

import java.util.List;

public class ReservationConverter {


    public static ReservationsMulti dtoToArrivalDeparture(CreateReservationArrivaDeparturelDto createReservationDto) {
        return ReservationsMulti.builder()
                .arrival(createReservationDto.arrival())
                .departure(createReservationDto.arrival())
                .build();
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

