package com.gerps.magazine.services.impl;

import com.gerps.magazine.dto.OrderItemDto;
import com.gerps.magazine.entity.OrderOperation;
import com.gerps.magazine.repository.OrderOperationsRepository;
import com.gerps.magazine.services.OrderOperationService;
import com.gerps.magazine.services.ProductsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

    public boolean checkOrderItemInStock(List<OrderItemDto> orderItems) {
        logger.info("Check if all products from the order are in stock");

        boolean allInStock = false;

        for (OrderItemDto orderItem: orderItems) {
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
