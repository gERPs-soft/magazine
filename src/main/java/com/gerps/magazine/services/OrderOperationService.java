package com.gerps.magazine.services;

import com.gerps.magazine.dto.OrderItemDto;
import com.gerps.magazine.entity.OrderOperation;

import java.util.List;

/**
 * Created by Grzesiek on 2018-11-23
 */

public interface OrderOperationService {

    void saveOperation(List<OrderOperation> operations);
    boolean checkOrderItemInStock(List<OrderItemDto> orderItems);
}
