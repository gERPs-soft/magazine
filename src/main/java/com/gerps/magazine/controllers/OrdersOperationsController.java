package com.gerps.magazine.controllers;

import com.gerps.magazine.dto.OrderDto;
import com.gerps.magazine.dto.OrderStatusDetails;
import com.gerps.magazine.entity.OrderOperation;
import com.gerps.magazine.entity.OrderStatus;
import com.gerps.magazine.services.OrderOperationService;
import com.gerps.magazine.services.ProductsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.List;

/**
 * Created by Grzesiek on 2018-11-21
 */

@RestController
@RequestMapping("/magazine/orders")
@CrossOrigin(origins = "http://localhost:4200")
public class OrdersOperationsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrdersOperationsController.class);
    private ProductsService productsService;
    private OrderOperationService orderOperationService;

    @Value("${orders.server.address}")
    private String orderUrlServer;

    @Autowired
    public OrdersOperationsController(ProductsService productsService, OrderOperationService orderOperationService) {
        this.productsService = productsService;
        this.orderOperationService = orderOperationService;
    }

    @PostMapping(value = "/add-order")
    private ResponseEntity<OrderStatusDetails> addNewOrder(@RequestBody OrderDto orderDto) {

        if (orderOperationService.findAllOperationsByOrderId(orderDto.getOrderId()).isEmpty()) {
            LOGGER.info("Add new order from customer {} with {} items.", orderDto.getCustomerId(), orderDto.getItems().size());

            List<OrderOperation> operationList = orderOperationService.orderItemsToOrderOperationsList(orderDto);

            orderOperationService.saveOperation(operationList);
            OrderStatusDetails orderStatusDetails = orderOperationService.confirmOrder(operationList);

            return new ResponseEntity(orderStatusDetails, HttpStatus.CREATED);
        } else {
            LOGGER.info("Order {} is already exists in databases", orderDto.getOrderId());
            return new ResponseEntity("Order " + orderDto.getOrderId() + " is already exists in databases", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/change-status-order")
    public ResponseEntity changeStatusInOrder(@RequestParam Long orderId, OrderStatus changedStatus) {

        RestTemplate restTemplate = new RestTemplate();
        List<OrderOperation> orderOperToChangeStatus = orderOperationService.findAllOperationsByOrderId(orderId);
        OrderStatusDetails orderStatusDetailsPastChange = new OrderStatusDetails(orderId, orderOperToChangeStatus.get(1).getShippingOrderDate(), "", changedStatus);
        ResponseEntity responseEntity = restTemplate.postForEntity(orderUrlServer + "/order/update_status", orderStatusDetailsPastChange, ResponseEntity.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            orderOperToChangeStatus.forEach(orderOperation -> orderOperation.setOrderStatus(changedStatus));
            orderOperationService.saveOperation(orderOperToChangeStatus);
            LOGGER.info("Order id={} status changed to {}", orderId, changedStatus);
            return new ResponseEntity(null, HttpStatus.CREATED);
        } else {
            LOGGER.warn("Status order id={} could not be changed", orderId);
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/change-status")
    public ResponseEntity changeStatusOrderInMagazine(@RequestBody OrderStatusDetails orderStatusDetails){

        List<OrderOperation> orderOperToChangeStatus = orderOperationService.findAllOperationsByOrderId(orderStatusDetails.getOrderId());
        orderOperToChangeStatus.forEach(orderOperation -> orderOperation.setOrderStatus(orderStatusDetails.getOrderStatus()));
        orderOperationService.saveOperation(orderOperToChangeStatus);

        return new ResponseEntity(orderStatusDetails, HttpStatus.CREATED);
    }
}