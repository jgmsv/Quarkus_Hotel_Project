package org.mindera.util.exceptions.room;

import org.mindera.util.messages.MessagesExceptions;

public class RoomPriceException extends RoomException {
    public RoomPriceException(String message) {
        super(MessagesExceptions.PRICEERROR);
    }

}
