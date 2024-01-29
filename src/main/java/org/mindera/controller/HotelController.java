package org.mindera.controller;


import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.mindera.dto.CreateHotelDto;
import org.mindera.dto.CreateReservationDto;
import org.mindera.dto.HotelGetDto;
import org.mindera.model.Hotel;
import org.mindera.service.HotelService;
import org.mindera.util.exceptions.HotelException;

import java.util.List;

@Path("api/v1/hotel")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class HotelController {

    @Inject
    HotelService hotelService;

    @POST
    @Path("/add")

    public Response add(CreateHotelDto hotel) {
        return Response.ok(add(hotel)).build();
    }

    public Response finAll(){
        List<HotelGetDto> findAllHottel = hotelService.findAll();
        return Response.ok(findAllHottel).build();
    }
    @PUT
    @Path("/updateReservation/{hotelN}/{roomNumber}")
    public Response update(String hotelN, int roomNumber, CreateReservationDto reservations) {

        return Response.ok(update(hotelN, roomNumber, reservations)).build();
    }


    @GET
    @Path("/getHotel/{hotelN}")
    public Response findHotelByHotelN(String hotelN){
        return Response.ok(findHotelByHotelN(hotelN)).build();
    }

}


