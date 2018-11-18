package com.gerps.magazine.services.impl;

import com.gerps.magazine.entity.Product;
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

    @Autowired
    public ProductsServiceImpl(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    @Override
    public List<Product> findAllProducts() {
        logger.info("find all products()");

        List<Product> productList = new ArrayList<>();
        productsRepository.findAll().forEach(productList::add);
        return productList;
    }

    @Override
    public Optional<Product> findProductById(Long id) {
        logger.info("findProduct by id:{}", id);
        return productsRepository.findById(id);
    }

    @Override
    public void save(Product product) {
        
    }

    @Override
    public void delete(Product product) {

    }
}
