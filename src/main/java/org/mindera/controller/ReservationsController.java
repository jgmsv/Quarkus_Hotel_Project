package org.mindera.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;
import org.mindera.dto.reservations.CreateReservationCheckOutDto;
import org.mindera.dto.reservations.CreateReservationDto;
import org.mindera.service.reservations.ReservationService;
import org.mindera.util.exceptions.hotel.HotelExistsException;
import org.mindera.util.exceptions.reservations.InvalidDateReservationException;
import org.mindera.util.exceptions.room.RoomExistsException;

@Path("api/v1/reservations")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ReservationsController {

    @Inject
    ReservationService reservationService;

    @POST
    public Response add(@PathParam("hotelN") String hotelN, @PathParam("roomNumber") int roomNumber, CreateReservationDto reservation) throws RoomExistsException, HotelExistsException, InvalidDateReservationException {
        return Response.ok(
                reservationService.addReservationToRoom(hotelN, roomNumber, reservation)).build();
    }

    @PUT
    public Response update(@PathParam("hotelN") String hotelN, @PathParam("roomNumber") int roomNumber, CreateReservationCheckOutDto reservation, ObjectId reservationId) throws RoomExistsException, HotelExistsException, InvalidDateReservationException {
        return Response.ok(
                reservationService.updateCheckOutDate(hotelN, roomNumber, reservation, reservationId)).build();
    }

    @GET
    public Response findAll() {
        return Response.ok(
                reservationService.findAllReservations()).build();
    }

    @GET
    public Response findById(@PathParam("id") ObjectId id) {
        return Response.ok(
                reservationService.findReservationById(id)).build();
    }
}
