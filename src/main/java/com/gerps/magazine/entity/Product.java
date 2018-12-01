package com.gerps.magazine.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

/**
 * Created by Grzesiek on 2018-11-17
 */

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String assort_index;

    @NotEmpty
    private String name;

    @ManyToOne
    @JoinColumn(name = "product_group_id")
    private ProductGroup product_group;

    @Enumerated(EnumType.STRING)
    private UnitOfMasure unitOfMasure;

    private String barcode;
    private Double weight_unit;

    @Enumerated(EnumType.STRING)
    private PackageUnit packageUnit;

    private Integer number_in_package;
    private Integer height;
    private Integer weight;
    private Integer length;
    private Integer number_in_pallet;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    private Integer stock;
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private Vat vat;

    /*//created only to tests
    public Product(String name) {
        this.name = name;
    }*/

    public Product(String assort_index, String name, ProductGroup product_group, UnitOfMasure unitOfMasure, String barcode, Double weight_unit, PackageUnit packageUnit, Integer number_in_package, Integer height, Integer weight, Integer length, Integer number_in_pallet, Supplier supplier, Integer stock, BigDecimal price, Vat vat) {
        this.assort_index = assort_index;
        this.name = name;
        this.product_group = product_group;
        this.unitOfMasure = unitOfMasure;
        this.barcode = barcode;
        this.weight_unit = weight_unit;
        this.packageUnit = packageUnit;
        this.number_in_package = number_in_package;
        this.height = height;
        this.weight = weight;
        this.length = length;
        this.number_in_pallet = number_in_pallet;
        this.supplier = supplier;
        this.stock = stock;
        this.price = price;
        this.vat = vat;
    }
}
