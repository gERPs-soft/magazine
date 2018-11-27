package com.gerps.magazine.services.impl;

import com.gerps.magazine.dto.OrderStatusDetails;
import com.gerps.magazine.entity.OrderOperation;
import com.gerps.magazine.entity.OrderStatus;
import com.gerps.magazine.repository.OrderOperationsRepository;
import com.gerps.magazine.services.OrderOperationService;
import com.gerps.magazine.services.ProductsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Grzesiek on 2018-11-23
 */

@Service
public class OrderOperationServiceImpl implements OrderOperationService {

    private static final Logger logger = LoggerFactory.getLogger(OrderOperationServiceImpl.class);
    private OrderOperationsRepository orderOperationsRepository;
    private ProductsService productsService;

    @Autowired
    public OrderOperationServiceImpl(OrderOperationsRepository orderOperationsRepository, ProductsService productsService) {
        this.orderOperationsRepository = orderOperationsRepository;
        this.productsService = productsService;
    }

    @Override
    public void saveOperation(List<OrderOperation> operations) {
        logger.info("Save new {} operations.", operations.size());
        orderOperationsRepository.saveAll(operations);
    }

    @Override
    public OrderStatusDetails confirmOrder(List<OrderOperation> orderItems) {

        LocalDateTime deliveryTime = LocalDateTime.now();
        OrderStatusDetails statusDetails;
        Long orderNumber = orderItems.get(0).getOrderNumber();

        if (checkOrderItemInStock(orderItems)) {
            logger.info("All products in stock");

            deliveryTime = deliveryTime.plusDays(2);
            String deliveryMessage = "All items from order " + orderNumber + " are in stock.\nDelivery time " + deliveryTime;

            statusDetails = new OrderStatusDetails(orderNumber, deliveryTime, deliveryMessage, OrderStatus.CONFIRMED);
        } else {
            logger.error("Stock is to low");

            deliveryTime = deliveryTime.plusDays(4);
            String deliveryMessage = "Not all products are in stock.\nDelivery time " + deliveryTime;
            statusDetails = new OrderStatusDetails(orderNumber, deliveryTime, deliveryMessage, OrderStatus.CONFIRMED);
        }

        return statusDetails;
    }

    public boolean checkOrderItemInStock(List<OrderOperation> orderItems) {
        logger.info("Check if all products from the order are in stock");

        boolean allInStock = false;

        for (OrderOperation orderItem : orderItems) {
            Integer stock = productsService.findProductById(orderItem.getProductId()).getStock();

            if (orderItem.getQuantity() > stock) {
                allInStock = false;
                return allInStock;
            } else {
                allInStock = true;
            }
        }
        return allInStock;
    }

}
