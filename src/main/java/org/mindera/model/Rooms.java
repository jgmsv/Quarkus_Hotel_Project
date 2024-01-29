package org.mindera.model;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Rooms extends PanacheMongoEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    int roomNumber;
    private RoomType roomType;
    private Reservations reservations;

    public Rooms() {
    }
}
