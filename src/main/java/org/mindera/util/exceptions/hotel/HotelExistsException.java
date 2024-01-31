package org.mindera.util.exceptions.hotel;

import org.mindera.util.messages.MessagesExceptions;

public class HotelExistsException extends HotelException {

    public HotelExistsException(String message) {
        super(MessagesExceptions.HOTELERROR);
    }

}
