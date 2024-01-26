package org.mindera.dto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.mindera.model.Rooms;

import java.util.Set;

@Builder
@Getter
@Setter
public record CreateHotelDto (
        String hotelN,
        String location,
        Set<Rooms> rooms
){


}
