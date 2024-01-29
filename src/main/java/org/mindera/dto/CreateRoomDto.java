package org.mindera.dto;

import jakarta.persistence.Column;
import lombok.*;
import org.mindera.model.Reservations;
import org.mindera.model.RoomType;


public record CreateRoomDto(
        int roomNumber,
        RoomType roomType,
        boolean available,
        CreateReservationDto reservations
) {


    }
