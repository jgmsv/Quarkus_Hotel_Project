package org.mindera.util.exceptions.reservations;

import org.mindera.util.messages.MessagesExceptions;

public class InvalidReservationIdException extends ReservationException {
    public InvalidReservationIdException(String message) {
        super(MessagesExceptions.INVALIDRESERVATIONID);
    }
}
