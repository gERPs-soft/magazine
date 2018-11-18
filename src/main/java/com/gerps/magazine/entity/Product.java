package com.gerps.magazine.entity;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by Grzesiek on 2018-11-17
 */

@Entity
@NoArgsConstructor
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    private String assort_index;
    private String name;

    @ManyToOne
    private ProductGroup product_group;

    private String PKWiU;
    private String unit;
    private String barcode;
    private Double weight_unit;

    @ManyToOne
    private PackageUnit package_unit;

    private Integer number_in_package;
    private Integer height;
    private Integer weight;
    private Integer lenght;
    private Integer number_in_pallet;

    @ManyToOne
    private Supplier supplier;

    private Integer stock;
    private BigDecimal price_last_supply;

    @Enumerated
    private Vat vat;

}
