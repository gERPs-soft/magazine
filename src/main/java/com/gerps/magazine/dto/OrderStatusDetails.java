package com.gerps.magazine.dto;

import com.gerps.magazine.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Created by Grzesiek on 2018-11-27
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatusDetails {
    private Long orderId;
    private LocalDateTime sendDate;
    private String additionalMessage;
    private OrderStatus orderStatus;
}