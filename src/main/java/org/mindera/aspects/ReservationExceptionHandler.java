package org.mindera.aspects;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import org.mindera.util.exceptions.reservations.InvalidDateReservationException;
import org.mindera.util.exceptions.reservations.InvalidReservationIdException;
import org.mindera.util.exceptions.reservations.ReservationException;

public class ReservationExceptionHandler implements ExceptionMapper<ReservationException> {
    @Override
    public Response toResponse(ReservationException e) {
        if (e instanceof InvalidReservationIdException) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid Reservation ID").build();
        } else if (e instanceof InvalidDateReservationException) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid Date").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("An error occurred").build();
        }
    }
}
