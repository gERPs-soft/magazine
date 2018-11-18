package com.gerps.magazine.converters;

import com.gerps.magazine.dto.ProductDto;
import com.gerps.magazine.entity.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * Created by Grzesiek on 2018-11-18
 */

@Component
public class ProductDtoConverter implements Function<Product, ProductDto> {

    private final static Logger logger = LoggerFactory.getLogger(ProductDtoConverter.class);

    @Override
    public ProductDto apply(Product product) {

        //logger.info("ProductDtoConverter()");

        return new ProductDto(product.getId(), product.getAssort_index(), product.getName(), product.getProduct_group().getId(),
                product.getPKWiU(), product.getUnit(), product.getBarcode(), product.getWeight_unit(), product.getPackage_unit().getId(),
                product.getNumber_in_package(), product.getHeight(), product.getWeight(), product.getLength(), product.getSupplier().getId(), product.getStock(),
                product.getVat().getVatValue());
    }
}