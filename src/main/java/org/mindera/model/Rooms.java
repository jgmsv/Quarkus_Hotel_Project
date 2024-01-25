package org.mindera.model;

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
public class Rooms {
    private Long id;
    private RoomType roomType;
    private boolean available;
    private Reservations reservations;

}
