package org.mindera.converter;

import org.mindera.dto.CreateRoomDto;
import org.mindera.model.Rooms;

public class RoomsConverter {

    public static Rooms dtoToRooms (CreateRoomDto createRoomDto){
        return Rooms.builder()
                .roomType(createRoomDto.roomType())
                .reservations((createRoomDto.reservations()))
                .build();
    }

}
