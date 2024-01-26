package org.mindera.dto;

import lombok.*;
import org.mindera.model.Reservations;
import org.mindera.model.RoomType;

@Builder
@Getter
@Setter
public record CreateRoomDto(
        RoomType roomType,
        boolean available,
        Reservations reservations
) {


    }
