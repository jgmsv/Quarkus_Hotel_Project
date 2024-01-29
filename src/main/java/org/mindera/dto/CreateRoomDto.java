package org.mindera.dto;


import org.mindera.model.RoomType;


public record CreateRoomDto(
        int roomNumber,
        RoomType roomType,
        boolean available,
        CreateReservationDto reservations
) {


}
