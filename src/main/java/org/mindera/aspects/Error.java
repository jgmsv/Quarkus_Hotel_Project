package org.mindera.aspects;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class Error {
    private String message;
    private int status;
    private Date timestamp;
}