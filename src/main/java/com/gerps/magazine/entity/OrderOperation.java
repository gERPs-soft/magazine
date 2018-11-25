package com.gerps.magazine.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by Grzesiek on 2018-11-21
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderOperation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime localDateTime;
    private Long productId;
    private Integer quantity;
    private Long sellerId;
    private Long customerId;
    private BigDecimal productPrice;
    private OrderStatus orderStatus;
}
