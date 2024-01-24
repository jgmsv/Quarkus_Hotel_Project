package org.mindera.model;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import org.bson.types.ObjectId;

import java.util.Set;

public class Hotel extends PanacheMongoEntity {
    private ObjectId id;
    private String hotelN;
    private String location;
    private Set<Rooms> rooms;



}
