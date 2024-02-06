package org.mindera.converter.reservations;

import org.mindera.dto.reservations.CreateRoomsReservationDto;
import org.mindera.model.reservations.RoomReservations;

import java.util.List;
import java.util.stream.Collectors;

public class ReservationRoomsConverter {

    public static RoomReservations dtoToRoomReservations(CreateRoomsReservationDto createRoomsReservationDto, int roomNumber) {
        return RoomReservations.builder()
                .roomNumber(roomNumber)
                .roomType(createRoomsReservationDto.roomType())
                .build();
    }


    public static List<RoomReservations> dtoToRoomsReservationList(List<CreateRoomsReservationDto> createRoomsReservationDtos) {
        return createRoomsReservationDtos.stream()
                .map(createRoomsReservationDto -> dtoToRoomReservations(createRoomsReservationDto, createRoomsReservationDtos.indexOf(createRoomsReservationDto)))
                .collect(Collectors.toList());
    }

}
