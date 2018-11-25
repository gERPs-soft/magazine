package com.gerps.magazine.converters;

import com.gerps.magazine.dto.ProductDto;
import com.gerps.magazine.entity.Product;
import org.springframework.stereotype.Component;
import java.util.function.Function;

/**
 * Created by Grzesiek on 2018-11-18
 */

@Component
public class ProductDtoConverter implements Function<Product, ProductDto> {

    //@todo zaimplementuj dynamiczne wyliczanie stock produktów, na podstawie zamówień itp.

    @Override
    public ProductDto apply(Product product) {

        return new ProductDto(product.getId(), product.getAssort_index(), product.getName(), product.getProduct_group().getId(),
                product.getUnitOfMasure().name(), product.getBarcode(), product.getWeight_unit(),
                product.getPackageUnit().name(), product.getNumber_in_package(),
                product.getHeight(), product.getWeight(), product.getLength(),
                product.getSupplier().getId(), product.getStock(),
                product.getVat().name());
    }
}