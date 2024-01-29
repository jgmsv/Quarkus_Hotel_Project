package org.mindera.util.exceptions;

import org.mindera.util.messages.Messages;

public class RoomException extends Exception {
    public RoomException(String message){
        super(Messages.ROOMERROR);
    }
    }

