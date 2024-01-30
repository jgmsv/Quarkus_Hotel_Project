package org.mindera.controller;


import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.mindera.dto.CreateHotelDto;
import org.mindera.dto.CreateReservationCheckInDto;
import org.mindera.dto.CreateReservationCheckOutDto;
import org.mindera.dto.HotelGetDto;
import org.mindera.service.HotelService;
import org.mindera.util.exceptions.HotelDuplication;
import org.mindera.util.exceptions.HotelException;
import org.mindera.util.exceptions.RoomException;

import java.util.List;

@Path("api/v1/hotel")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class HotelController {

    @Inject
    HotelService hotelService;

    @POST
    public Response add(CreateHotelDto hotel) throws HotelException, HotelDuplication {
        return Response.ok(
                hotelService.addHotel(hotel)).build();
    }

    @GET
    public Response finAll() {
        List<HotelGetDto> findAllHottel = hotelService.findAllHotels();
        return Response.ok(findAllHottel).build();
    }

    @PUT
    @Path("/updateReservation/{hotelN}/{roomNumber}")
    public Response updateCheckIn(@PathParam("hotelN") String hotelN, @PathParam("roomNumber") int roomNumber, CreateReservationCheckInDto reservations) throws RoomException, HotelException {
        return Response.ok(hotelService.updateRoomCheckInDate(hotelN, roomNumber, reservations)).build();
    }

    @PUT
    @Path("/updateReservation/{hotelN}/{roomNumber}")
    public Response updateCheckOut(@PathParam("hotelN") String hotelN, @PathParam("roomNumber") int roomNumber, CreateReservationCheckOutDto reservations) throws RoomException, HotelException {
        return Response.ok(hotelService.updateRoomCheckOutDate(hotelN, roomNumber, reservations)).build();
    }

    @PUT
    @Path("/updatePrice/{hotelN}/{roomNumber}/{price}")
    public Response updatePrice(@PathParam("hotelN") String hotelN, @PathParam("roomNumber") int roomNumber, @PathParam("price") int price) throws RoomException, HotelException {
        return Response.ok(hotelService.updateRoomPrice(hotelN, roomNumber, price)).build();
    }

    @GET
    @Path("/HotelName/{hotelN}")
    public Response findHotelByHotelN(@PathParam("hotelN") String hotelN) throws HotelException {
        return Response.ok(hotelService.findHotelByHotelN(hotelN)).build();
    }

}


