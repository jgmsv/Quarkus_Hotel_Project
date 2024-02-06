package org.mindera.model.reservations;

import lombok.*;
import org.mindera.model.hotel.RoomType;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomReservations {
    private int roomNumber;

    private RoomType roomType;
}
