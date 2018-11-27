package com.gerps.magazine.controllers;

import com.gerps.magazine.dto.OrderDto;
import com.gerps.magazine.dto.OrderItemDto;
import com.gerps.magazine.dto.OrderStatusDetails;
import com.gerps.magazine.entity.OrderOperation;
import com.gerps.magazine.entity.OrderStatus;
import com.gerps.magazine.dto.MyResponseDetails;
import com.gerps.magazine.services.OrderOperationService;
import com.gerps.magazine.services.ProductsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Grzesiek on 2018-11-21
 */

@RestController
@RequestMapping("magazine/orders")
@CrossOrigin(origins = "http://localhost:4200")
public class OrdersOperationsController {

    private static final Logger logger = LoggerFactory.getLogger(OrdersOperationsController.class);
    private ProductsService productsService;
    private OrderOperationService orderOperationService;

    @Autowired
    public OrdersOperationsController(ProductsService productsService, OrderOperationService orderOperationService) {
        this.productsService = productsService;
        this.orderOperationService = orderOperationService;
    }

    @PostMapping("/add-order")
    private ResponseEntity addNewOrder(@RequestBody OrderDto orderDto) {

        Long orderNumber = orderDto.getId();
        Long sellerId = orderDto.getSellerId();
        Long customerId = orderDto.getCustomerId();
        List<OrderItemDto> orderItems = orderDto.getItems();

        logger.info("Add new order={} for customer {} with {} items.", orderDto.getId(), customerId, orderItems.size());
        List<OrderOperation> operationList = new ArrayList<>();

        orderItems.forEach(orderItemDto -> {
            OrderOperation operation = new OrderOperation();
            operation.setOrderNumber(orderNumber);
            operation.setLocalDateTime(LocalDateTime.now());
            operation.setProductId(orderItemDto.getProductId());
            operation.setQuantity(orderItemDto.getQuantity());
            operation.setSellerId(sellerId);
            operation.setCustomerId(customerId);
            operation.setProductPrice(orderItemDto.getProductPrice());
            operation.setOrderStatus(OrderStatus.CONFIRMED);
            operationList.add(operation);
        });
        orderOperationService.saveOperation(operationList);

        LocalDateTime deliveryTime = LocalDateTime.now();

        if (orderOperationService.checkOrderItemInStock(orderItems)) {
            logger.info("All products in stock");

            deliveryTime = deliveryTime.plusDays(2);
            String deliveryMessage = "All items from order " + orderNumber + " are in stock.\nDelivery time " + deliveryTime;

            OrderStatusDetails statusDetails = new OrderStatusDetails(orderNumber, deliveryTime, deliveryMessage, OrderStatus.CONFIRMED);
            return new ResponseEntity(statusDetails, HttpStatus.CREATED);
        } else {
            logger.error("Stock is to low");

            deliveryTime = deliveryTime.plusDays(4);
            String deliveryMessage = "Not all products are in stock.\nDelivery time " + deliveryTime;
            OrderStatusDetails statusDetails = new OrderStatusDetails(orderNumber, deliveryTime, deliveryMessage, OrderStatus.CONFIRMED);

            return new ResponseEntity(statusDetails, HttpStatus.CREATED);
        }
    }
}