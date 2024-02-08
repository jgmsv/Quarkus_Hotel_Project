package org.mindera.aspects;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.mindera.util.exceptions.room.RoomException;
import org.mindera.util.exceptions.room.RoomExistsException;
import org.mindera.util.messages.MessagesExceptions;

/**
 * Exception handler for RoomException.
 * This class is responsible for mapping RoomException to appropriate HTTP responses.
 */
@Provider
public class RoomExceptionHandeler implements ExceptionMapper<RoomException> {
    /**
     * Maps RoomException to appropriate HTTP response.
     * If the exception is of type RoomExistsException, it returns a BAD_REQUEST response with the message ROOMERROR.
     * Otherwise, it returns a BAD_REQUEST response with the message ANERROROCCURRED.
     *
     * @param e the RoomException to be mapped
     * @return the HTTP response
     */
    @Override
    public Response toResponse(RoomException e) {
        if (e instanceof RoomExistsException) {
            return Response.status(Response.Status.BAD_REQUEST).entity(MessagesExceptions.ROOMERROR).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(MessagesExceptions.ANERROROCCURRED).build();
    }
}
