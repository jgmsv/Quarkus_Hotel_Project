package org.mindera.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Reservations {
    private Long id;
    private Long roomId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
}
