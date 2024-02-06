package org.mindera.model.reservations;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@MongoEntity(collection = "Reservations", database = "reservationmultidb")
public class ReservationsMulti extends PanacheMongoEntity {

    private LocalDate arrival;
    private LocalDate departure;
    private String hotelN;
    private String firstName;
    private String lastName;
    private int phoneNumber;
    private int vat;
    private List<RoomReservations> roomReservations;
}
