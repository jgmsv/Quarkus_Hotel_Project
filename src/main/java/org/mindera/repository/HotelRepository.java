package org.mindera.repository;

import com.speedment.jpastreamer.application.JPAStreamer;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.mindera.model.Hotel;

import java.util.Optional;
@ApplicationScoped
public class HotelRepository implements PanacheMongoRepository<Hotel> {

    @Inject
    JPAStreamer jpaStreamer;

    public Optional<Hotel> findByHotelN(String hotelN) {
        return jpaStreamer.stream(Hotel.class)
                .filter("hotelN = :hotelName", Parameters.with("hotelName", hotelN))
                .findFirst();
    }

    public Optional<Hotel> findByLocation (String location) {

    }

}
