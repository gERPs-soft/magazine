package com.gerps.magazine.controllers;

import com.gerps.magazine.dto.OrderDto;
import com.gerps.magazine.dto.OrderItemDto;
import com.gerps.magazine.dto.OrderStatusDetails;
import com.gerps.magazine.entity.OrderOperation;
import com.gerps.magazine.entity.OrderStatus;
import com.gerps.magazine.services.OrderOperationService;
import com.gerps.magazine.services.ProductsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Value("${orders.server.address}")
    private String orderUrlServer;

    @Autowired
    public OrdersOperationsController(ProductsService productsService, OrderOperationService orderOperationService) {
        this.productsService = productsService;
        this.orderOperationService = orderOperationService;
    }

    @PostMapping(value = "/add-order"  /*, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE*/)
    private ResponseEntity addNewOrder(@RequestBody OrderDto orderDto) {

        Long orderNumber = orderDto.getOrderId();
        Long sellerId = orderDto.getSellerId();
        Long customerId = orderDto.getCustomerId();
        List<OrderItemDto> orderItems = orderDto.getItems();

        logger.info("Add new order from customer {} with {} items.", customerId, orderItems.size());

        List<OrderOperation> operationList = new ArrayList<>();

        orderItems.forEach(orderItemDto -> {
            OrderOperation operation = new OrderOperation();
            operation.setOrderNumber(orderNumber);
            operation.setOrderDate(LocalDateTime.now());
            operation.setProductId(orderItemDto.getProductId());
            operation.setQuantity(orderItemDto.getQuantity());
            operation.setSellerId(sellerId);
            operation.setCustomerId(customerId);
            operation.setProductPrice(orderItemDto.getProductPrice());
            operation.setShippingOrderDate(LocalDateTime.now());
            operation.setOrderStatus(OrderStatus.CONFIRMED);
            operationList.add(operation);
        });

        orderOperationService.saveOperation(operationList);

        OrderStatusDetails orderStatusDetails = orderOperationService.confirmOrder(operationList);

        return new ResponseEntity(orderStatusDetails, HttpStatus.CREATED);
    }

    @PostMapping("/change-status-order")
    public ResponseEntity changeStatusOrder(@RequestParam Long orderId, OrderStatus changedStatus) {

        RestTemplate restTemplate = new RestTemplate();
        List<OrderOperation> orderOperToChangeStatus = orderOperationService.findAllOperationsByOrderId(orderId);
        OrderStatusDetails orderStatusDetails = new OrderStatusDetails(orderId, orderOperToChangeStatus.get(1).getShippingOrderDate(), "", changedStatus);
        ResponseEntity responseEntity = restTemplate.postForEntity(orderUrlServer + "/order/update_status", orderStatusDetails, ResponseEntity.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            //orderOperationService.changeStatusOrderOp(orderId, changedStatus);
            orderOperToChangeStatus.forEach(orderOperation -> orderOperation.setOrderStatus(changedStatus));
            orderOperationService.saveOperation(orderOperToChangeStatus);
            return new ResponseEntity(null, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}