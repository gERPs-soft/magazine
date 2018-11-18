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
public class ProductConverter implements Function<ProductDto, Product> {

    private final static Logger logger = LoggerFactory.getLogger(ProductConverter.class);

    @Override
    public Product apply(ProductDto productDto) {
        logger.info("ProductConverter.apply()");

        return new Product();
    }
}
