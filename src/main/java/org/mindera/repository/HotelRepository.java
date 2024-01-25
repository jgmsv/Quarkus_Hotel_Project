package org.mindera.repository;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import org.mindera.model.Hotel;

import java.util.Optional;

public interface HotelRepository extends PanacheMongoRepository<Hotel> {

    Optional<Hotel> findByhotelN (String hotelN);
    Optional<Hotel> findBylocation (String location);


}
