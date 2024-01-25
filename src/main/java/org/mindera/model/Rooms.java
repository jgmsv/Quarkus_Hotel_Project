package org.mindera.model;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Rooms extends PanacheMongoEntity {
    private RoomType roomType;
    private boolean available;
    private Reservations reservations;

}
