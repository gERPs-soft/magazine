package com.gerps.magazine.dto;

import lombok.Data;

import java.util.Date;

/**
 * Created by Grzesiek on 2018-11-18
 */

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
