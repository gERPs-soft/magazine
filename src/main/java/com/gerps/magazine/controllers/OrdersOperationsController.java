package com.gerps.magazine.controllers;

import com.gerps.magazine.converters.OrderDtoToOrderOpConverter;
import com.gerps.magazine.dto.OrderDto;
import com.gerps.magazine.dto.OrderItemDto;
import com.gerps.magazine.entity.OrderOperation;
import com.gerps.magazine.entity.OrderStatus;
import com.gerps.magazine.services.OrderOperationService;
import com.gerps.magazine.services.ProductsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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
    @CrossOrigin(origins = "http://localhost:4200")
    private void addNewOrder(@RequestBody OrderDto orderDto) {

        Long sellerId = orderDto.getSellerId();
        Long customerId = orderDto.getCustomerId();
        List<OrderItemDto> orderItems = orderDto.getItems();

        logger.info("Add new order={} for customer {} with {} items.", orderDto.getId(), customerId, orderItems.size());
        List<OrderOperation> operationList = new ArrayList<>();

        orderItems.stream().forEach(orderItemDto -> {
            OrderOperation operation = new OrderOperation();
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

    }

    //metodka testowa wysylajaca POSTem JSONA z zamowienie
    private static void addNewExampleOrder() {
        RestTemplate restTemplate = new RestTemplate();
        String jsonToSent = "{\"name\":\"mouse\",\"price\":5}";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");

        HttpEntity httpEntity = new HttpEntity(jsonToSent, httpHeaders);

        ResponseEntity exchange = restTemplate.exchange(
                "http://localhost:8080/addProduct",
                HttpMethod.POST,
                httpEntity,
                Void.class);
    }
}