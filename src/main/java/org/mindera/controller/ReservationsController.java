package org.mindera.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
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

    @Operation(summary = "Add a new reservation")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "reservation added"),
            @APIResponse(responseCode = "400", description = "reservation duplicated"),
            @APIResponse(responseCode = "400", description = "Hotel not found"),
            @APIResponse(responseCode = "400", description = "Room not found")
    })
    @POST
    @Path("/{hotelN}/")
    public Response add(CreateReservationMultiDto reservation) throws HotelExistsException, RoomExistsException, ReservationExistsException {
        return Response.status(Response.Status.CREATED).entity(
                reservationService.addReservation(reservation)).build();
    }

    @Operation(summary = "Update reservation arrival and departure")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "reservation updated"),
            @APIResponse(responseCode = "400", description = "reservation not found")
    })
    @PUT
    @Path("/update/{reservationId}")
    public Response updateReservation(@PathParam("reservationId") ObjectId reservationId, CreateReservationArrivaDeparturelDto updateReservation) throws InvalidDateReservationException, RoomExistsException, HotelExistsException, ReservationExistsException {
        return Response.ok(
                reservationService.updateReservation(reservationId, updateReservation)).build();
    }

    @Operation(summary = "Find all reservations")
    @APIResponse(responseCode = "200", description = "Reservations found")
    @GET
    public Response findAll(@QueryParam("page") int page) {
        return Response.ok(
                reservationService.findAllReservations(page)).build();
    }

    @Operation(summary = "Find reservation by id")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "reservation found"),
            @APIResponse(responseCode = "400", description = "reservation not found")
    })
    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") ObjectId id) {
        return Response.ok(
                reservationService.findReservationById(id)).build();
    }
}
