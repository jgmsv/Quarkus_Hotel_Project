package org.mindera.model;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class Reservations extends PanacheMongoEntity {
    private int reservationId;
    private boolean available = true;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    public Reservations() {
    }

    public Reservations(int reservationId, boolean available, LocalDate checkInDate) {
        this.reservationId = reservationId;
        this.available = available;
        this.checkInDate = checkInDate;
    }

    public Reservations(int reservationId, LocalDate checkOutDate) {
        this.reservationId = reservationId;
        this.checkOutDate = checkOutDate;
        setAvailable(true);
    }

    public Reservations(int reservationId, boolean available, LocalDate checkInDate, LocalDate checkOutDate) {
        this.reservationId = reservationId;
        this.available = available;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public void changeAvailability() {
        if (checkInDate != null) {
            available = false;
        }
    }
}
