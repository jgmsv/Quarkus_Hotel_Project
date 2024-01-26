package org.mindera.service;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.mindera.dto.CreateHotelDto;
import org.mindera.dto.HotelGetDto;
import org.mindera.model.Hotel;
import org.mindera.repository.HotelRepository;

import java.util.List;

import static org.mindera.converter.HotelConverter.dtoToHotel;
import static org.mindera.converter.HotelConverter.hotelToDtoList;

@ApplicationScoped
@Transactional
public class HotelService {

    @Inject
    HotelRepository hotelRepository;

    public Hotel addHotel(CreateHotelDto createHotelDto) {
        Hotel hotel = dtoToHotel(createHotelDto);
        hotelRepository.persist(hotel);
        return hotel;
    }

    public List<HotelGetDto> findAll() {
        PanacheQuery<Hotel> hotelsQuery = hotelRepository.findAll();
        List<Hotel> hotels = hotelsQuery.list();
        return hotelToDtoList(hotels);
    }
}
