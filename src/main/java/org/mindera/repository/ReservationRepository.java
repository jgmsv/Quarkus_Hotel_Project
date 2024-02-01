package org.mindera.repository;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.mindera.model.reservations.Reservations;

@ApplicationScoped
public class ReservationRepository implements PanacheMongoRepository<Reservations> {

}
