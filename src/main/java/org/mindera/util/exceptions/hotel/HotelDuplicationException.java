package org.mindera.util.exceptions.hotel;

import org.mindera.util.messages.MessagesExceptions;

public class HotelDuplicationException extends HotelException {

    public HotelDuplicationException(String message) {
        super(MessagesExceptions.DUPLICATEDHOTEL);
    }

}


