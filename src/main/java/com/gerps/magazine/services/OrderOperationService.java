package com.gerps.magazine.services;

import com.gerps.magazine.dto.OrderItemDto;
import com.gerps.magazine.dto.OrderStatusDetails;
import com.gerps.magazine.entity.OrderOperation;

import java.util.List;

/**
 * Created by Grzesiek on 2018-11-23
 */

public interface OrderOperationService {

    List<OrderOperation> findAllOperationsByOrderId(Long orderId);

    void saveOperation(List<OrderOperation> operations);

    boolean checkOrderItemsInStock(List<OrderOperation> orderItems);

    OrderStatusDetails confirmOrder(List<OrderOperation> orderItems);
}
