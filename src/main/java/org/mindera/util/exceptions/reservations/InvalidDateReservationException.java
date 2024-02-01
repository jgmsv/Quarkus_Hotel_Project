package org.mindera.util.exceptions.reservations;

import org.mindera.util.messages.MessagesExceptions;

public class InvalidDateReservationException extends ReservationException {
    public InvalidDateReservationException(String message) {
        super(MessagesExceptions.INVALIDDATE);
    }


}
