package org.mindera.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mindera.model.Reservations;
import org.mindera.model.RoomType;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CrateRoomDto {

        private Long id;
        private RoomType roomType;
        private boolean available;
        private Reservations reservations;

    }
