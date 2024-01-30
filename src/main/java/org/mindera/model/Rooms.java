package org.mindera.model;

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
    private Reservations reservations;


    public Rooms() {
    }

}
