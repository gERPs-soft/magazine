package com.gerps.magazine.entity;

import com.fasterxml.jackson.annotation.JacksonInject;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by Grzesiek on 2018-11-17
 */

@Entity
@NoArgsConstructor
@Data
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    private String assort_index;
    private String name;

    @ManyToOne
    @JoinColumn(name = "product_group_id")
    private ProductGroup product_group;

    private String PKWiU;
    private String unit;
    private String barcode;
    private Double weight_unit;

    @ManyToOne
    @JoinColumn(name = "package_unit_id")
    private PackageUnit package_unit;

    private Integer number_in_package;
    private Integer height;
    private Integer weight;
    private Integer length;
    private Integer number_in_pallet;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    private Integer stock;
    private BigDecimal price_last_supply;

    @Enumerated(EnumType.STRING)
    private Vat vat;



}
