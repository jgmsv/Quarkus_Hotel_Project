package org.mindera.util.exceptions.reservations;

import org.mindera.util.messages.MessagesExceptions;

public class ReservationException extends Exception {
    public ReservationException(String message) {
        super(message);
    }

    public ReservationException() {
        super(MessagesExceptions.ANERROROCCURRED);
    }
}
