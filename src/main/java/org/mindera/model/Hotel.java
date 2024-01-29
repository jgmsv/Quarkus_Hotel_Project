package org.mindera.model;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@MongoEntity(collection = "Hotels", database = "my-mongodb")
public class Hotel extends PanacheMongoEntity {
    private String hotelN;
    private String location;
    private Set<Rooms> rooms;

}
