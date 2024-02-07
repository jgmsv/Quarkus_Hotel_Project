package org.mindera.dto.hotel;

import org.mindera.model.hotel.RoomType;

import java.util.List;


public record HotelRoomTypeGetDto(

        String hotelN,
        List<RoomType> hotelRoomType
) {

}
