package org.mindera.util.exceptions.hotel;

import org.mindera.util.messages.MessagesExceptions;

public class HotelException extends Exception {

    public HotelException(String message) {
        super(MessagesExceptions.ANERROROCCURRED);
    }
}
