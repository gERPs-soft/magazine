package com.gerps.magazine.entity;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by Grzesiek on 2018-11-17
 */

@Entity
@NoArgsConstructor
public class Assortment {

    @Id
    @GeneratedValue
    private Long id;

    private String assort_index;
    private String name;

    @ManyToOne
    private AssortmentGroup assortmentGroupId;

    private String PKWiU;
    private String unit;
    private String barcode;
    private Double weightUnit;

    @ManyToOne
    private PackageUnit packageUnitId;

    private Integer howManyInPackage;
    private Integer height;
    private Integer weight;
    private Integer lenght;
    private Integer howManyInPallet;

    @ManyToOne
    private Supplier supplierId;

    private Long quantityInStock;
    private BigDecimal priceLastSupply;

    @Enumerated
    private Vat vat;
}
