package org.mindera.dto;
import io.quarkus.mongodb.panache.PanacheMongoEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.mindera.model.Rooms;

import java.util.Set;

public record CreateHotelDto (
    String hotelN,
    String location,
    Set<CreateRoomDto> rooms
){


}
