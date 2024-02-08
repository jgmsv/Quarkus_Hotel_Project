package org.mindera.model.reservations;

import lombok.*;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.mindera.model.hotel.RoomType;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomReservations {

    @Schema(description = "Room number", example = "101")
    private int roomNumber;
    @Schema(description = "Rooms types for reservations", example = "room type: SINGLEROOM")
    private RoomType roomType;
}
