package org.mindera.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.mindera.model.Hotel;

import java.util.Optional;

@ApplicationScoped
public interface HotelRepository extends PanacheRepository<Hotel> {

    Optional<Hotel> findByHotelN (String hotelN);
}
