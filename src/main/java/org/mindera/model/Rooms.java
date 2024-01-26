package org.mindera.model;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Rooms extends PanacheMongoEntity {
    private RoomType roomType;
    private Reservations reservations;

    public Rooms() {
    }
}
