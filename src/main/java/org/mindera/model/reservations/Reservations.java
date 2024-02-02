package org.mindera.model.reservations;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@MongoEntity(collection = "Reservations", database = "reservationdb")
public class Reservations extends PanacheMongoEntity {

    private LocalDate arrival;
    private LocalDate departure;
    private String firstName;
    private String lastName;
    private int phoneNumber;
    private int vat;
    private String hotelN;
    private int roomNumber;

}

