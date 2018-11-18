package com.gerps.magazine.exceptions;

import lombok.Data;

import java.util.Date;

/**
 * Created by Grzesiek on 2018-11-18
 */
@Data
public class ErrorDetails {
    private Date timestamp;
    private String message;
    private String details;

    public ErrorDetails(Date timestamp, String message, String details) {
        super();
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }
}