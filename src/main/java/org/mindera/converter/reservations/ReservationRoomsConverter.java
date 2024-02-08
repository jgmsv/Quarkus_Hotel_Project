package org.mindera.converter.reservations;

import org.mindera.dto.reservations.CreateRoomsReservationDto;
import org.mindera.model.reservations.RoomReservations;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This class provides methods to convert between CreateRoomsReservationDto and RoomReservations objects.
 */
public class ReservationRoomsConverter {
    /**
     * Converts a CreateRoomsReservationDto object to a RoomReservations object.
     *
     * @param createRoomsReservationDto The CreateRoomsReservationDto object to be converted.
     * @param roomNumber                The room number for the RoomReservations object.
     * @return The converted RoomReservations object.
     */
    public static RoomReservations dtoToRoomReservations(CreateRoomsReservationDto createRoomsReservationDto, int roomNumber) {
        return RoomReservations.builder()
                .roomNumber(roomNumber)
                .roomType(createRoomsReservationDto.roomType())
                .build();
    }

    /**
     * Converts a list of CreateRoomsReservationDto objects to a list of RoomReservations objects.
     *
     * @param createRoomsReservationDtos The list of CreateRoomsReservationDto objects to be converted.
     * @return The converted list of RoomReservations objects.
     */
    public static List<RoomReservations> dtoToRoomsReservationList(List<CreateRoomsReservationDto> createRoomsReservationDtos) {
        return createRoomsReservationDtos.stream()
                .map(createRoomsReservationDto -> dtoToRoomReservations(createRoomsReservationDto, createRoomsReservationDtos.indexOf(createRoomsReservationDto)))
                .collect(Collectors.toList());
    }

}
