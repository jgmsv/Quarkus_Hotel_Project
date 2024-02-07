package org.mindera.repository;


import io.quarkus.mongodb.panache.PanacheMongoRepository;
import io.quarkus.mongodb.panache.PanacheQuery;
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
        PanacheQuery<Hotel> query = find("facilities in ?1", facilities.stream().findFirst().get());
        return query.list();
    }

    public List<Hotel> findByAddress(String location) {
        return find("location", location).list();
    }


    public boolean isHotelNUnique(String hotelN) {
        return find("hotelN", hotelN).count() == 0;
    }

    public boolean isHotelPhoneNumerUnique(String phoneNumber) {
        return find("phoneNumber", phoneNumber).count() == 0;
    }


}
