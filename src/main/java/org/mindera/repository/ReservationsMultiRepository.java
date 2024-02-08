package org.mindera.repository;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.mindera.model.reservations.ReservationsMulti;

/**
 * This class represents a repository for managing ReservationsMulti entities.
 */
@ApplicationScoped
public class ReservationsMultiRepository implements PanacheMongoRepository<ReservationsMulti> {
}
