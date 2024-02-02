package org.mindera.converter.hotel;

import org.mindera.dto.hotel.CreateRoomDto;
import org.mindera.model.hotel.Rooms;

import java.util.Set;
import java.util.stream.Collectors;

public class RoomsConverter {

    public static Rooms dtoToRooms(CreateRoomDto createRoomDto) {
        return Rooms.builder()
                .roomType(createRoomDto.roomType())
                .roomNumber(createRoomDto.roomNumber())
                .numberOfBeds(createRoomDto.numberOfBeds())
                .roomPrice(createRoomDto.roomPrice())
                .build();
    }


    public static Set<Rooms> dtoToRoomsSet(Set<CreateRoomDto> createRoomDtos) {
        return createRoomDtos.stream()
                .map(RoomsConverter::dtoToRooms)
                .collect(Collectors.toSet());
    }

}
