package org.mindera.repository;


import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.types.ObjectId;
import org.mindera.model.hotel.Hotel;
import org.mindera.model.hotel.Rooms;

import java.util.Optional;

@ApplicationScoped
public class HotelRepository implements PanacheMongoRepository<Hotel> {

    public Optional<Hotel> findByHotelN(String hotelN) {
        return find("hotelN", hotelN).firstResultOptional();
    }

    public Optional<Rooms> findByRoomId(ObjectId roomId) {
        return Rooms.findByIdOptional(roomId);
    }

    public boolean isHotelNUnique(String hotelN) {
        return find("hotelN", hotelN).count() == 0;
    }

    public Optional<Hotel> findByRoomType(String roomType) {
        return find("roomType", roomType).firstResultOptional();
    }
}
