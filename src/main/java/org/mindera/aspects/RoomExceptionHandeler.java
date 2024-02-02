package org.mindera.aspects;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.mindera.util.exceptions.room.RoomException;
import org.mindera.util.exceptions.room.RoomExistsException;
import org.mindera.util.messages.MessagesExceptions;

@Provider
public class RoomExceptionHandeler implements ExceptionMapper<RoomException> {
    @Override
    public Response toResponse(RoomException e) {
        if (e instanceof RoomExistsException) {
            return Response.status(Response.Status.BAD_REQUEST).entity(MessagesExceptions.ROOMERROR).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(MessagesExceptions.ANERROROCCURRED).build();
    }
}
