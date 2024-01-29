package org.mindera.model;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;
import org.bson.types.ObjectId;

import java.time.LocalDate;
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Reservations extends PanacheMongoEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private int reservationId;
    private boolean available;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    public Reservations() {
    }
}
