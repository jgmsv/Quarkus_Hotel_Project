package org.mindera.dto;
import lombok.Getter;
import lombok.Setter;
import org.mindera.model.Rooms;

import java.util.Set;
@Getter
@Setter
public class CreatHotelDto {
    private Long id;
    private String hotelN;
    private String location;
    private Set<Rooms> rooms;
}
