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

    int roomNumber;
    private RoomType roomType;
    private Reservations reservations;

    public Rooms() {
    }
}
