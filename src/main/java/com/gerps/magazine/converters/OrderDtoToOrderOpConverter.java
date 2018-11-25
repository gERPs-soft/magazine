package com.gerps.magazine.converters;

import com.gerps.magazine.dto.OrderDto;
import com.gerps.magazine.entity.OrderOperation;
import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * Created by Grzesiek on 2018-11-21
 */

@Component
public class OrderDtoToOrderOpConverter implements Function<OrderDto, OrderOperation> {

    @Override
    public OrderOperation apply(OrderDto orderDto) {

        return new OrderOperation();
    }
}

    /*
    private Long sellerId;
    private Long customerId;
    private List<OrderItemDto> items;
    private Long productId;
    private Integer quantity;
    private BigDecimal productPrice;
    */