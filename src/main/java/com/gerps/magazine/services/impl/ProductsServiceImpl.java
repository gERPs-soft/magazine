package com.gerps.magazine.services.impl;

import com.gerps.magazine.converters.ProductConverter;
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

/**
 * Created by Grzesiek on 2018-11-18
 */

@Service
public class ProductsServiceImpl implements ProductsService {

    private static final Logger logger = LoggerFactory.getLogger(ProductsServiceImpl.class);
    private ProductsRepository productsRepository;
    private ProductDtoConverter productDtoConverter;
    private ProductConverter productConverter;

    @Autowired
    public ProductsServiceImpl(ProductsRepository productsRepository, ProductDtoConverter productDtoConverter, ProductConverter productConverter) {
        this.productsRepository = productsRepository;
        this.productDtoConverter = productDtoConverter;
        this.productConverter = productConverter;
    }

    @Override
    public List<ProductDto> findAllProducts() throws EntityNotFoundException {
        logger.info("find all products()");

        // RW: rename to products.
        Iterable<Product> productList = productsRepository.findAll();

        if (productList != null) {
            List<ProductDto> productDtoList = new ArrayList<>();
            productList.forEach(product -> productDtoList.add(productDtoConverter.apply(product)));
            return productDtoList;
        } else {
            // RW: error is to high in this case. This is not a bug but rather situation where we have lack of certain assortments.
            logger.error("Products list is empty. Not found any products");
            throw new EntityNotFoundException("Products list is empty. No products found in database. Please try again later.");
        }
    }

    @Override
    public ProductDto findProductById(Long id)  throws EntityNotFoundException {

        Optional<Product> optionalProduct = productsRepository.findById(id);

        if (optionalProduct.isPresent()) {
            Product productIsPresent = optionalProduct.get();
            logger.info("Found product {}", productIsPresent.getName());
            return productDtoConverter.apply(productIsPresent);
        } else {
            logger.error("Not found product by orderId: {}", id);
            throw  new EntityNotFoundException("Product with orderId "+id+" was not found in database. Please try again with another orderId.");
        }
    }

    @Override
    public void save(Product product) {
        logger.info("Save to db product with name={}", product.getName());
        productsRepository.save(product);
    }

    @Override
    public void delete(Product product) {

    }
}
