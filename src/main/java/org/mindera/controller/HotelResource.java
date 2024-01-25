package org.mindera.controller;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.mindera.model.Hotel;
import org.mindera.repository.HotelRepository;

import java.util.Optional;

@Path("/")
public class HotelResource {


    HotelRepository hotelRepository;

    @GET
    @Path("helloWorld")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello(){
        return "Hello";
    }

    @GET
    @Path("hotel/{hotelName}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getHotel(String hotelName) {
        Optional<Hotel> hotel = hotelRepository.findByHotelN(hotelName);
        return hotel.isPresent() ? hotel.get().getRooms().toString() : "No hotel found";
    }
}
