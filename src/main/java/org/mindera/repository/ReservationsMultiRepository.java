package org.mindera.repository;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.mindera.model.reservations.ReservationsMulti;

@ApplicationScoped
public class ReservationsMultiRepository implements PanacheMongoRepository<ReservationsMulti> {
}
