package org.mindera.util.exceptions.room;

import org.mindera.util.messages.MessagesExceptions;

public class RoomExistsException extends RoomException {
    public RoomExistsException(String message) {
        super(MessagesExceptions.ROOMERROR);
    }
}

