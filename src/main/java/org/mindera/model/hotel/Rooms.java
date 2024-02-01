package org.mindera.model.hotel;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Rooms extends PanacheMongoEntity {
    private int roomNumber;
    private int numberOfBeds;
    private RoomType roomType;
    private int roomPrice;
    private boolean available;


    public Rooms() {
    }

}
