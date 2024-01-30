package org.mindera.dto;


import org.mindera.model.RoomType;


public record CreateRoomDto(
        int roomNumber,
        int numberOfBeds,
        RoomType roomType,
        int roomPrice,
        boolean available,
        CreateReservationDto reservations
) {


}
