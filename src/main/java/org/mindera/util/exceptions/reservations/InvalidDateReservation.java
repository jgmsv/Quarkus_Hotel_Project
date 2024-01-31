package org.mindera.util.exceptions.reservations;

import org.mindera.util.messages.MessagesExceptions;

public class InvalidDateReservation extends ReservationException {
    public InvalidDateReservation(String message) {
        super(MessagesExceptions.INVALIDDATE);
    }


}
