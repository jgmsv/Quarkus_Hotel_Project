package org.mindera.repository;


import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.mindera.model.hotel.Hotel;

import java.util.Optional;

@ApplicationScoped
public class HotelRepository implements PanacheMongoRepository<Hotel> {

    public Optional<Hotel> findByHotelN(String hotelN) {
        return find("hotelN", hotelN).firstResultOptional();
    }

    public boolean isHotelNUnique(String hotelN) {
        return find("hotelN", hotelN).count() == 0;
    }

    public boolean isHotelPhoneNumerUnique(String phoneNumber) {
        return find("phoneNumber", phoneNumber).count() == 0;
    }

}
