package org.mindera.dto;

import org.mindera.model.Rooms;

import java.util.Set;

public record HotelUpdateDto (
        Set<Rooms> rooms
)
{
}
