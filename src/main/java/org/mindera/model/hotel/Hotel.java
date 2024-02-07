package org.mindera.model.hotel;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@MongoEntity(collection = "Hotels", database = "hoteldb")

public class Hotel extends PanacheMongoEntity {
    private String hotelN;
    private String location;
    private String phoneNumber;
    private Set<Rooms> rooms;
    //private List<Tags> tags;


}
