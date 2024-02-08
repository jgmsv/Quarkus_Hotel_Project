package org.mindera.converter.reservations;

import org.mindera.dto.reservations.CreateReservationArrivaDeparturelDto;
import org.mindera.dto.reservations.CreateReservationMultiDto;
import org.mindera.dto.reservations.ReservationsMultiGetDto;
import org.mindera.model.reservations.ReservationsMulti;

import java.util.List;

/**
 * The ReservationConverter class provides methods to convert between different reservation-related DTOs and entities.
 */
public class ReservationConverter {
    /**
     * Converts a CreateReservationArrivaDeparturelDto object to a ReservationsMulti object.
     *
     * @param createReservationDto the CreateReservationArrivaDeparturelDto object to be converted
     * @return the converted ReservationsMulti object
     */
    public static ReservationsMulti dtoToArrivalDeparture(CreateReservationArrivaDeparturelDto createReservationDto) {
        return ReservationsMulti.builder()
                .arrival(createReservationDto.arrival())
                .departure(createReservationDto.arrival())
                .build();
    }

    /**
     * Converts a CreateReservationMultiDto object to a ReservationsMulti object.
     *
     * @param createReservationMultiDto the CreateReservationMultiDto object to be converted
     * @return the converted ReservationsMulti object
     */
    public static ReservationsMulti dtoToReservations(CreateReservationMultiDto createReservationMultiDto) {
        return ReservationsMulti.builder()
                .arrival(createReservationMultiDto.arrival())
                .departure(createReservationMultiDto.departure())
                .fullName(createReservationMultiDto.fullName())
                .phoneNumber(createReservationMultiDto.phoneNumber())
                .vat(createReservationMultiDto.vat())
                .hotelN(createReservationMultiDto.hotelN())
                .roomReservations(ReservationRoomsConverter.dtoToRoomsReservationList(createReservationMultiDto.roomReservations()))
                .build();
    }

    /**
     * Converts a ReservationsMulti object to a ReservationsMultiGetDto object.
     *
     * @param reservationsMulti the ReservationsMulti object to be converted
     * @return the converted ReservationsMultiGetDto object
     */
    public static ReservationsMultiGetDto reservationMultiToDto(ReservationsMulti reservationsMulti) {
        return new ReservationsMultiGetDto(
                reservationsMulti.getArrival(),
                reservationsMulti.getDeparture(),
                reservationsMulti.getHotelN(),
                reservationsMulti.getFullName(),
                reservationsMulti.getPhoneNumber(),
                reservationsMulti.getVat(),
                reservationsMulti.getRoomReservations(),
                reservationsMulti.id
        );
    }

    /**
     * Converts a list of ReservationsMulti objects to a list of ReservationsMultiGetDto objects.
     *
     * @param reservationsMulti the list of ReservationsMulti objects to be converted
     * @return the converted list of ReservationsMultiGetDto objects
     */
    public static List<ReservationsMultiGetDto> reservationsMultiToDtoList(List<ReservationsMulti> reservationsMulti) {
        return reservationsMulti.stream()
                .map(ReservationConverter::reservationMultiToDto)
                .toList();
    }
}

