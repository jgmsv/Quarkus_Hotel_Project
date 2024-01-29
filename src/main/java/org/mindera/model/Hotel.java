package org.mindera.model;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.Column;
import lombok.*;


import java.util.Set;
@Getter
@Setter
@Builder
@MongoEntity(collection = "Hotels", database = "my-mongodb")
@NoArgsConstructor
@AllArgsConstructor
public class Hotel extends PanacheMongoEntity {
    @Column(unique = true)
    private String hotelN;
    private String location;
    private Set<Rooms> rooms;


}
