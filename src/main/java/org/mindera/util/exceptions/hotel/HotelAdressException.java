package org.mindera.util.exceptions.hotel;

import org.mindera.util.messages.MessagesExceptions;

public class HotelAdressException extends HotelException {

    public HotelAdressException(String message) {
        super(MessagesExceptions.HOTELADDRESSNOTFOUND);
    }

}
