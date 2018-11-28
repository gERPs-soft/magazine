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
        logger.info("Save {} new OrderOperations to db.", operations.size());
        orderOperationsRepository.saveAll(operations);
    }

    @Override
    public List<OrderOperation> findAllOperationsByOrderId(Long orderId) {
        return orderOperationsRepository.findOrderOperationsByOrderNumber(orderId);
    }

    @Override
    public OrderStatusDetails confirmOrder(List<OrderOperation> orderItems) {

        LocalDateTime deliveryTime = LocalDateTime.now();
        OrderStatusDetails statusDetails;
        Long orderNumber = orderItems.get(1).getOrderNumber();

        if (checkOrderItemsInStock(orderItems)) {
            logger.info("All products in stock to order number {}", orderNumber);

            deliveryTime = deliveryTime.plusDays(2L);
            String deliveryMessage = "All items from order " + orderNumber + " are in stock.\nDelivery time " + deliveryTime;
            modifyDeliveryTimeInOrder(orderNumber, deliveryTime);
            statusDetails = new OrderStatusDetails(orderNumber, deliveryTime, deliveryMessage, OrderStatus.CONFIRMED);

        } else {
            logger.error("Stock is to low to order number {}", orderNumber);

            deliveryTime = deliveryTime.plusDays(4L);
            String deliveryMessage = "Sorry, we do not have all the ordered items in stock. The shipping time will be extended to 4 days";
            statusDetails = new OrderStatusDetails(orderNumber, deliveryTime, deliveryMessage, OrderStatus.CONFIRMED);
            modifyDeliveryTimeInOrder(orderNumber, deliveryTime);
        }
        return statusDetails;
    }

    private void modifyDeliveryTimeInOrder(Long orderId, LocalDateTime modifyDeliveryTime) {
        logger.info("Modify/set delivery time in order number {} and save to db.", orderId);

        List<OrderOperation> operationList = findAllOperationsByOrderId(orderId);
        operationList.forEach(orderOperation -> orderOperation.setShippingOrderDate(modifyDeliveryTime));

        saveOperation(operationList);
    }

    public boolean checkOrderItemsInStock(List<OrderOperation> orderOperations) {
        logger.info("Check if all products from the order {} are in stock", orderOperations.get(1).getOrderNumber());

        boolean allInStock = false;

        for (OrderOperation orderItem : orderOperations) {
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
