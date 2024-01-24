package org.mindera.model;

import io.quarkus.mongodb.panache.PanacheMongoEntityBase;
import org.bson.types.ObjectId;

public class Rooms {
    private ObjectId id;
    private RoomType roomType;
    private boolean available;
    private Reservations reservations;

}
