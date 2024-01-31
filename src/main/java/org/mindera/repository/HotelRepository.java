package org.mindera.repository;


import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.mindera.model.Hotel;

import java.util.Optional;

@ApplicationScoped
public class HotelRepository implements PanacheMongoRepository<Hotel> {

    public Optional<Hotel> findByHotelN(String hotelN) {
        return find("hotelN", hotelN).firstResultOptional();
    }

    public boolean isHotelNUnique(String hotelN) {
        return find("hotelN", hotelN).count() == 0;
    }
}
