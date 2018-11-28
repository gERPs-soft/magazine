package com.gerps.magazine.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gerps.magazine.entity.PackageUnit;
import com.gerps.magazine.entity.ProductGroup;
import com.gerps.magazine.entity.Supplier;
import com.gerps.magazine.entity.Vat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

/**
 * Created by Grzesiek on 2018-11-18
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
//@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDto {

    private Long id;

    private String assort_index;
    private String name;

    private Integer product_group;

    private String unitOfMasure;
    private String barcode;
    private Double weight_unit;

    private String package_unit;

    private Integer number_in_package;
    private Integer height;
    private Integer weight;
    private Integer length;

    private Long supplier;

    private Integer stock;
    private BigDecimal price;
    private String vat;

}
