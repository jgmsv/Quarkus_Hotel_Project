package org.mindera.model;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Reservations extends PanacheMongoEntity {

    private int reservationId;
    private boolean available;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    public Reservations() {
    }
}
