package com.gerps.magazine.dto;

import com.gerps.magazine.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Created by Grzesiek on 2018-11-27
 */

@Data
@AllArgsConstructor
public class OrderStatusDetails {
    private Long orderId;
    private LocalDateTime localDateTime;
    private String message;
    private OrderStatus orderStatus;
}