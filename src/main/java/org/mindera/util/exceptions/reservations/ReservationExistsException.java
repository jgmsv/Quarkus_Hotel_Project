package org.mindera.util.exceptions.reservations;

import org.mindera.util.messages.MessagesExceptions;

public class ReservationExistsException extends ReservationException {
    public ReservationExistsException(String message) {
        super(MessagesExceptions.RESERVATIONEXISTS);
    }
}
