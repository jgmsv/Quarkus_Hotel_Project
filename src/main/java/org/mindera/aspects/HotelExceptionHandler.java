package org.mindera.aspects;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.mindera.util.exceptions.hotel.*;
import org.mindera.util.messages.MessagesExceptions;

@Provider
public class HotelExceptionHandler implements ExceptionMapper<HotelException> {


    @Override
    public Response toResponse(HotelException e) {
        if (e instanceof HotelDuplicationException) {
            return Response.status(Response.Status.BAD_REQUEST).entity(MessagesExceptions.DUPLICATEDHOTEL).build();
        } else if (e instanceof HotelExistsException) {
            return Response.status(Response.Status.BAD_REQUEST).entity(MessagesExceptions.HOTELERROR).build();
        } else if (e instanceof HotelAdressException) {
            return Response.status(Response.Status.BAD_REQUEST).entity(MessagesExceptions.HOTELADDRESSNOTFOUND).build();
        } else if (e instanceof HotelFacilitiesException) {
            return Response.status(Response.Status.BAD_REQUEST).entity(MessagesExceptions.HOTELFACILITIESNOTFOUND).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity(MessagesExceptions.ANERROROCCURRED).build();
        }
    }
}
