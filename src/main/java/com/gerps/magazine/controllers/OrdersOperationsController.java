package com.gerps.magazine.controllers;

import com.gerps.magazine.converters.OrderDtoToOrderOpConverter;
import com.gerps.magazine.dto.OrderDto;
import com.gerps.magazine.dto.OrderItemDto;
import com.gerps.magazine.entity.OrderOperation;
import com.gerps.magazine.entity.OrderStatus;
import com.gerps.magazine.exceptions.ResponseDetails;
import com.gerps.magazine.services.OrderOperationService;
import com.gerps.magazine.services.ProductsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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
            operation.setOrderStatus(OrderStatus.DRAFT);
            operationList.add(operation);
        });

        orderOperationService.saveOperation(operationList);

        if (orderOperationService.checkOrderItemInStock(orderItems)) {
            logger.info("All products in stock");

            String deliveryMessage = "All items from order "+orderNumber+" are in stock.\nDelivery time "+ LocalDate.now().plusDays(2);
            ResponseDetails details = new ResponseDetails(new Date(), deliveryMessage);
            return new ResponseEntity(details, HttpStatus.CREATED);
        } else {
            logger.error("Stock is to low");

            String deliveryMessage = "Not all products are in stock.\nDelivery time "+LocalDate.now().plusDays(4);
            ResponseDetails details = new ResponseDetails(new Date(), deliveryMessage);
            return new ResponseEntity(details, HttpStatus.CREATED);
        }
    }
}