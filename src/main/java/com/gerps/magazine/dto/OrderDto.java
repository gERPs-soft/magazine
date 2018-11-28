package com.gerps.magazine.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by Grzesiek on 2018-11-21
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
//This is OrderDto from Order Api
public class OrderDto {

    private Long orderId;
    private Long sellerId;
    private Long customerId;
    private List<OrderItemDto> items;

}
