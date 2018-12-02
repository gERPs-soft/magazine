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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    // RW: as it is constant id should be written in uppercase manner, like: LOGGER
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
    private ResponseEntity<OrderStatusDetails> addNewOrder(@RequestBody OrderDto orderDto) {

        //RW: you do not need these variables. Of course it is not a bad style but thanks to this you have more lines to read
        // and no significant value in this case from extraction ;)
        Long orderNumber = orderDto.getOrderId();
        Long sellerId = orderDto.getSellerId();
        Long customerId = orderDto.getCustomerId();
        List<OrderItemDto> orderItems = orderDto.getItems();

        logger.info("Add new order from customer {} with {} items.", customerId, orderItems.size());

        List<OrderOperation> operationList = new ArrayList<>();

        //RW: this lambda can be extracted to separate private method. Code will be more cleaner and easy to read.
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

        //RW: I would suggest to change return type of this endpoint to: ResponseEntity<OrderStatusDetails>.
        // It is easier to find out what value it will return on success.
        return new ResponseEntity(orderStatusDetails, HttpStatus.CREATED);

        //@todo dodaj sprawdzanie czy zamówienie o przesyłanym id już istnieje
    }

    @PostMapping("/change-status-order")
    public ResponseEntity changeStatusOrder(@RequestParam Long orderId, OrderStatus changedStatus) {

        // RW: rest template should be invoked from service layer, so the whole logic can be moved to service layer.
        // On this level you should have light controlers and try to pack the whole logic into services.
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