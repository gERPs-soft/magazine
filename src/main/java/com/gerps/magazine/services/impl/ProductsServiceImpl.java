package com.gerps.magazine.services.impl;

import com.gerps.magazine.converters.ProductDtoConverter;
import com.gerps.magazine.dto.ProductDto;
import com.gerps.magazine.entity.Product;
import com.gerps.magazine.exceptions.EntityNotFoundException;
import com.gerps.magazine.repository.ProductsRepository;
import com.gerps.magazine.services.ProductsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public List<ProductDto> findAllProducts() throws EntityNotFoundException {
        logger.info("find all products()");

        Iterable<Product> productList = productsRepository.findAll();
        if (productList != null) {
            List<ProductDto> productDtoList = new ArrayList<>();
            productList.forEach(product -> productDtoList.add(productDtoConverter.apply(product)));
            return productDtoList;
        } else {
            logger.error("Products list is empty. Not found any products");
            throw new EntityNotFoundException("Products list is empty. No products found in the database. Please try again later.");
        }
    }

    @Override
    public ProductDto findProductById(Long id)  throws EntityNotFoundException {
        logger.info("findProduct by id:{}", id);

        Optional<Product> optionalProduct = productsRepository.findById(id);

        if (optionalProduct.isPresent()) {
            Product productIsPresent = optionalProduct.get();
            logger.info("Found product {}", productIsPresent.getName());
            return productDtoConverter.apply(productIsPresent);
        } else {
            logger.error("Not found product by id: {}", id);
            throw  new EntityNotFoundException("Product with id "+id+" was not found in database. Please try again with another id.");
        }
    }

    @Override
    public void save(Product product) {

    }

    @Override
    public void delete(Product product) {

    }
}
