package org.mindera.model;

import org.bson.types.ObjectId;

import java.time.LocalDate;

public class Reservations {
    private ObjectId id;
    private ObjectId roomId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
}
