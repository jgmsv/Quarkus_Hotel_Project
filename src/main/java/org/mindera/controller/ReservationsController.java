package org.mindera.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;
import org.mindera.dto.reservations.CreateReservationArrivaDeparturelDto;
import org.mindera.dto.reservations.CreateReservationMultiDto;
import org.mindera.service.reservations.ReservationService;
import org.mindera.util.exceptions.hotel.HotelExistsException;
import org.mindera.util.exceptions.reservations.InvalidDateReservationException;
import org.mindera.util.exceptions.reservations.ReservationExistsException;
import org.mindera.util.exceptions.room.RoomExistsException;

@Path("api/v1/reservations")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ReservationsController {

    @Inject
    ReservationService reservationService;

    /*@POST
    @Path("/{hotelN}/{roomNumber}")
    public Response add(@PathParam("hotelN") String hotelN, @PathParam("roomNumber") RoomType roomType, CreateReservationDto reservation) throws RoomExistsException, HotelExistsException, InvalidDateReservationException, ReservationExistsException {
        return Response.ok(reservationService.addReservationToRoom(hotelN, roomType, reservation)).status(Response.Status.CREATED).build();

    }*/

    @POST
    @Path("/{hotelN}/")
    public Response addmulti(@PathParam("hotelN") String hotelN, CreateReservationMultiDto reservation) throws RoomExistsException, HotelExistsException, InvalidDateReservationException, ReservationExistsException {
        return Response.status(Response.Status.CREATED).entity(
                reservationService.addReservation(hotelN, reservation)).build();
    }

    @PUT
    @Path("/update/{reservationId}")
    public Response updateReservation(@PathParam("reservationId") ObjectId reservationId, CreateReservationArrivaDeparturelDto updateReservation) throws InvalidDateReservationException, ReservationExistsException, RoomExistsException, HotelExistsException {
        return Response.ok(
                reservationService.updateReservation(reservationId, updateReservation)).build();
    }

    @GET
    public Response findAll(@QueryParam("page") int page) {
        return Response.ok(
                reservationService.findAllReservations(page)).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") ObjectId id) {
        return Response.ok(
                reservationService.findReservationById(id)).build();
    }
}
