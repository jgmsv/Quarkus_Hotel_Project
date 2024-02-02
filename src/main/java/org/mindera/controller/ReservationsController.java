package org.mindera.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;
import org.mindera.dto.reservations.CreateReservationDto;
import org.mindera.service.reservations.ReservationService;
import org.mindera.util.exceptions.hotel.HotelExistsException;
import org.mindera.util.exceptions.reservations.InvalidDateReservationException;
import org.mindera.util.exceptions.reservations.ReservationExistsException;
import org.mindera.util.exceptions.room.RoomExistsException;

import java.time.LocalDate;

@Path("api/v1/reservations")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ReservationsController {

    @Inject
    ReservationService reservationService;

    @POST
    @Path("/{hotelN}/{roomNumber}")
    public Response add(@PathParam("hotelN") String hotelN, @PathParam("roomNumber") int roomNumber, CreateReservationDto reservation, LocalDate arrival, LocalDate departure) throws RoomExistsException, HotelExistsException, InvalidDateReservationException, ReservationExistsException {
        return Response.ok(
                reservationService.addReservationToRoom(hotelN, roomNumber, reservation, arrival, departure)).build();
    }

    @PUT
    @Path("/arrival/{reservationId}")
    public Response updateArrival(@PathParam("reservationId") ObjectId reservationId, LocalDate arrival) throws InvalidDateReservationException {
        return Response.ok(
                reservationService.updateArrival(reservationId, arrival)).build();
    }

    @PUT
    @Path("/departure/{reservationId}")
    public Response updateDeparture(@PathParam("reservationId") ObjectId reservationId, LocalDate departure) throws InvalidDateReservationException {
        return Response.ok(
                reservationService.updateDeparture(reservationId, departure)).build();
    }

    @GET
    public Response findAll() {
        return Response.ok(
                reservationService.findAllReservations()).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") ObjectId id) {
        return Response.ok(
                reservationService.findReservationById(id)).build();
    }
}
