package org.mindera.model;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import lombok.*;
import org.bson.types.ObjectId;

import java.time.LocalDate;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Reservations extends PanacheMongoEntity {
    private boolean available;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    public Reservations() {
    }
}
