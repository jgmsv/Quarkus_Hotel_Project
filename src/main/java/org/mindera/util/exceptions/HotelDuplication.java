package org.mindera.util.exceptions;

import org.mindera.util.messages.Messages;

public class HotelDuplication extends Exception {

    public HotelDuplication(String message) {
        super(Messages.DUPLICATEDHOTEL);
    }

}
