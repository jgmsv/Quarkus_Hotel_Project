package org.mindera.model;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.util.Set;
@Getter
@Setter
@MongoEntity(collection = "Hotels", database = "my-mongodb")
@ApplicationScoped
public class Hotel extends PanacheMongoEntity {
    private Long id;
    private String hotelN;
    private String location;
    private Set<Rooms> rooms;

}
