package org.mindera.dto;

import org.mindera.model.Rooms;

import java.util.Set;

public record HotelGetDto(
        String hotelN,
        String location,
        Set<Rooms> rooms
) {

    //Separar as reserva
    //Criar

}
