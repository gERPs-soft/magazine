package com.gerps.magazine.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Grzesiek on 2018-11-18
 */

@Data
public class ResponseDetails implements Serializable {
    private Date timestamp;
    private String message;

    public ResponseDetails(Date timestamp, String message) {
        super();
        this.timestamp = timestamp;
        this.message = message;
    }
}
