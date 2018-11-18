package com.gerps.magazine.services.impl;

import com.gerps.magazine.converters.ProductDtoConverter;
import com.gerps.magazine.dto.ProductDto;
import com.gerps.magazine.entity.Product;
import com.gerps.magazine.repository.ProductsRepository;
import com.gerps.magazine.services.ProductsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by Grzesiek on 2018-11-18
 */

@Service
public class ProductsServiceImpl implements ProductsService {

    private static final Logger logger = LoggerFactory.getLogger(ProductsServiceImpl.class);
    private ProductsRepository productsRepository;
    private ProductDtoConverter productDtoConverter;

    @Autowired
    public ProductsServiceImpl(ProductsRepository productsRepository, ProductDtoConverter productDtoConverter) {
        this.productsRepository = productsRepository;
        this.productDtoConverter = productDtoConverter;
    }

    @Override
    public List<ProductDto> findAllProducts() {
        logger.info("find all products()");

        Iterable<Product> productList = productsRepository.findAll();

        return StreamSupport.stream(productList.spliterator(), true)
                .map(productDtoConverter)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto findProductById(Long id) {
        logger.info("findProduct by id:{}", id);

        Optional<Product> product = productsRepository.findById(id);

        if (product.isPresent()) {
            Product productIsPresent = product.get();
            logger.info("Found product {}", productIsPresent.getName());
            return productDtoConverter.apply(productIsPresent);
        } else {
            logger.error("Not found product by id:{}", id);
            return new ProductDto();
        }
    }

    @Override
    public void save(Product product) {

    }

    @Override
    public void delete(Product product) {

    }
}
