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
import org.mindera.util.exceptions.hotel.HotelDuplicationException;
import org.mindera.util.exceptions.hotel.HotelExistsException;
import org.mindera.util.exceptions.reservations.InvalidDateReservation;
import org.mindera.util.exceptions.room.RoomExistsException;
import org.mindera.util.exceptions.room.RoomPriceException;

import java.util.List;

@Path("api/v1/hotel")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class HotelController {

    @Inject
    HotelService hotelService;

    @POST
    public Response add(CreateHotelDto hotel) throws HotelExistsException, HotelDuplicationException {
        return Response.ok(
                hotelService.addHotel(hotel)).build();
    }

    @GET
    public Response finAll() {
        List<HotelGetDto> findAllHottel = hotelService.findAllHotels();
        return Response.ok(findAllHottel).build();
    }

    @PUT
    @Path("/updateCheckIn/{hotelN}/{roomNumber}")
    public Response updateCheckIn(@PathParam("hotelN") String hotelN, @PathParam("roomNumber") int roomNumber, CreateReservationCheckInDto reservations) throws RoomExistsException, HotelExistsException, InvalidDateReservation {
        return Response.ok(hotelService.updateRoomCheckInDate(hotelN, roomNumber, reservations)).build();
    }

    @PUT
    @Path("/updateCheckOut/{hotelN}/{roomNumber}")
    public Response updateCheckOut(@PathParam("hotelN") String hotelN, @PathParam("roomNumber") int roomNumber, CreateReservationCheckOutDto reservations) throws RoomExistsException, HotelExistsException, InvalidDateReservation {
        return Response.ok(hotelService.updateRoomCheckOutDate(hotelN, roomNumber, reservations)).build();
    }

    @PUT
    @Path("/updatePrice/{hotelN}/{roomNumber}/{price}")
    public Response updatePrice(@PathParam("hotelN") String hotelN, @PathParam("roomNumber") int roomNumber, @PathParam("price") int price) throws RoomExistsException, HotelExistsException, RoomPriceException {
        return Response.ok(hotelService.updateRoomPrice(hotelN, roomNumber, price)).build();
    }

    @GET
    @Path("/HotelName/{hotelN}")
    public Response findHotelByHotelN(@PathParam("hotelN") String hotelN) throws HotelExistsException {
        return Response.ok(hotelService.findHotelByHotelN(hotelN)).build();
    }

}


