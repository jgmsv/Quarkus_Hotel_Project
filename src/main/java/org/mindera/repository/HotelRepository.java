package org.mindera.repository;


import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.mindera.model.hotel.Facilities;
import org.mindera.model.hotel.Hotel;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class HotelRepository implements PanacheMongoRepository<Hotel> {

    public Optional<Hotel> findByHotelN(String hotelN) {
        return find("hotelN", hotelN).firstResultOptional();
    }

    public List<Hotel> findByFacilities(List<Facilities> facilities) {
        return find("?1 in facilities", facilities.getFirst()).list();
    }

    public List<Hotel> findByAddress(String location, int page) {
        return find("location", location).page(page, 10).list();
    }


    public boolean isHotelNUnique(String hotelN) {
        return find("hotelN", hotelN).count() == 0;
    }

    public boolean isHotelPhoneNumerUnique(String phoneNumber) {
        return find("phoneNumber", phoneNumber).count() == 0;
    }


}
