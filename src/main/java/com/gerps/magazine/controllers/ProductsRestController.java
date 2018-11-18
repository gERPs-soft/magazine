package com.gerps.magazine.controllers;

import com.gerps.magazine.entity.Product;
import com.gerps.magazine.services.ProductsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

/**
 * Created by Grzesiek on 2018-11-18
 */

@RestController
public class ProductsRestController {

    private static final Logger logger = LoggerFactory.getLogger(ProductsRestController.class);

    @Autowired
    private ProductsService productsService;

    @RequestMapping("/products/all")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<Product> getAllProducts(){

        return Collections.emptyList();
    }
}
