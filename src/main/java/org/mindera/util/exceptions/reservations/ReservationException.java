package org.mindera.util.exceptions.reservations;

import org.mindera.util.messages.MessagesExceptions;

public class ReservationException extends Exception {
    public ReservationException(String message) {
        super(MessagesExceptions.ANERROROCCURRED);
    }
}
