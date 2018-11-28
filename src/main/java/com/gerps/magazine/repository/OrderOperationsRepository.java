package com.gerps.magazine.repository;

import com.gerps.magazine.entity.OrderOperation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

/**
 * Created by Grzesiek on 2018-11-23
 */

@Repository
@CrossOrigin(origins = "http://localhost:4200")
public interface OrderOperationsRepository extends CrudRepository<OrderOperation, Long> {
    List<OrderOperation> findOrderOperationsByOrderNumber(Long id);
}
