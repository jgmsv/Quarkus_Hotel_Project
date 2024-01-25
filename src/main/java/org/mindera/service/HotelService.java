package org.mindera.service;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.mindera.model.Hotel;
import org.mindera.repository.HotelRepository;

import java.util.List;
import java.util.Optional;
@Path("api/hotel")
@Produces(MediaType.APPLICATION_JSON)
public class HotelService {


    @POST
    public Hotel add(Hotel hotel) {
        hotel.persistOrUpdate();
        return hotel;
    }
    @GET
    public List<Hotel> getHotels(){
        return Hotel.listAll();
    }

}
