package org.mindera.repository;


import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.mindera.model.hotel.Hotel;

import java.util.List;
import java.util.Optional;

/**
 * This class represents a repository for managing Hotel entities.
 * It extends the PanacheMongoRepository class, which provides default CRUD operations for MongoDB.
 */
@ApplicationScoped
public class HotelRepository implements PanacheMongoRepository<Hotel> {
    /**
     * Finds a hotel by its name.
     *
     * @param hotelN the name of the hotel
     * @return an Optional containing the hotel if found, or an empty Optional if not found
     */
    public Optional<Hotel> findByHotelN(String hotelN) {
        return find("hotelN", hotelN).firstResultOptional();
    }

    /**
     * Finds hotels by their address.
     *
     * @param location the location/address of the hotels
     * @param page     the page number for pagination
     * @return a list of hotels that match the specified location and page number
     */
    public List<Hotel> findByAddress(String location, int page) {
        return find("location", location).page(page, 10).list();
    }

    /**
     * Checks if the hotel name is unique.
     *
     * @param hotelN the name of the hotel to check
     * @return true if the hotel name is unique, false otherwise
     */
    public boolean isHotelNUnique(String hotelN) {
        return find("hotelN", hotelN).count() == 0;
    }

    /**
     * Checks if the hotel phone number is unique.
     *
     * @param phoneNumber the phone number of the hotel to check
     * @return true if the hotel phone number is unique, false otherwise
     */
    public boolean isHotelPhoneNumerUnique(String phoneNumber) {
        return find("phoneNumber", phoneNumber).count() == 0;
    }


}
