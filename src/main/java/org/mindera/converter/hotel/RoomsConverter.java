package org.mindera.converter.hotel;

import org.mindera.dto.hotel.CreateRoomDto;
import org.mindera.model.hotel.Rooms;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * The RoomsConverter class provides methods to convert CreateRoomDto objects to Rooms objects.
 */
public class RoomsConverter {
    /**
     * Converts a CreateRoomDto object to a Rooms object.
     *
     * @param createRoomDto the CreateRoomDto object to be converted
     * @return the converted Rooms object
     */
    public static Rooms dtoToRooms(CreateRoomDto createRoomDto) {
        return Rooms.builder()
                .roomType(createRoomDto.roomType())
                .roomNumber(createRoomDto.roomNumber())
                .numberOfBeds(createRoomDto.numberOfBeds())
                .roomPrice(createRoomDto.roomPrice())
                .build();
    }

    /**
     * Converts a set of CreateRoomDto objects to a set of Rooms objects.
     *
     * @param createRoomDtos the set of CreateRoomDto objects to be converted
     * @return the set of converted Rooms objects
     */
    public static Set<Rooms> dtoToRoomsSet(Set<CreateRoomDto> createRoomDtos) {
        return createRoomDtos.stream()
                .map(RoomsConverter::dtoToRooms)
                .collect(Collectors.toSet());
    }

}
