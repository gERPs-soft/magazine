package com.gerps.magazine.dto;

import lombok.Data;

import java.util.Date;

/**
 * Created by Grzesiek on 2018-11-18
 */

// RW: name like this 'MyResponseDetails' is also not a good idea.
@Data
public class MyResponseDetails {
    private Date timestamp;
    private String message;

    public MyResponseDetails(Date timestamp, String message) {
        super();
        this.timestamp = timestamp;
        this.message = message;
    }
}
