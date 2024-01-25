package org.mindera.model;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.PanacheMongoEntityBase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Rooms extends PanacheMongoEntity {
    private RoomType roomType;
    private boolean available;
    private Reservations reservations;

}
