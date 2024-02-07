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
    private String fullName;
    private String phoneNumber;
    private String vat;
    private List<RoomReservations> roomReservations;
}
