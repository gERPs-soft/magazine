package com.gerps.magazine.controllers;

import com.gerps.magazine.dto.ProductDto;
import com.gerps.magazine.services.ProductsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Grzesiek on 2018-11-18
 */

@RestController
public class ProductsRestController {

    private static final Logger logger = LoggerFactory.getLogger(ProductsRestController.class);
    private ProductsService productsService;

    @Autowired
    public ProductsRestController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @RequestMapping("/products/all")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<ProductDto> findAllProducts() {
        logger.info("Rest findAllProducts()");
        return productsService.findAllProducts();
    }

    @RequestMapping("/products/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<ProductDto> findProductById(@PathVariable Long id) {
        logger.info("Rest findProductById={}", id);

        ProductDto productDto = productsService.findProductById(id);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }
}

