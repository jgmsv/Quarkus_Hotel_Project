package org.mindera.util.exceptions.room;

import org.mindera.util.messages.MessagesExceptions;

public class RoomException extends Exception {
    public RoomException(String message) {
        super(message);
    }

    public RoomException() {
        super(MessagesExceptions.ANERROROCCURRED);
    }
}
