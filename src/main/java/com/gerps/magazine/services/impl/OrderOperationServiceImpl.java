package com.gerps.magazine.services.impl;

import com.gerps.magazine.entity.OrderOperation;
import com.gerps.magazine.repository.OrderOperationsRepository;
import com.gerps.magazine.services.OrderOperationService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Grzesiek on 2018-11-23
 */

@Service
public class OrderOperationServiceImpl implements OrderOperationService {

    private static final Logger logger = LoggerFactory.getLogger(OrderOperationServiceImpl.class);
    private OrderOperationsRepository orderOperationsRepository;

    @Autowired
    public OrderOperationServiceImpl(OrderOperationsRepository orderOperationsRepository) {
        this.orderOperationsRepository = orderOperationsRepository;
    }

    @Override
    public void saveOperation(List<OrderOperation> operations) {
        logger.info("Save new {} operations.", operations.size());
        orderOperationsRepository.saveAll(operations);
    }
}
