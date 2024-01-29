package org.mindera.util.exceptions;

import org.mindera.util.messages.Messages;

public class HotelException extends Exception{

    public HotelException(String message){
        super(Messages.HOTELERROR);
    }

}
