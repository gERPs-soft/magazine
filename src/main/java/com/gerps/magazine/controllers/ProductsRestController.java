package com.gerps.magazine.controllers;

import com.gerps.magazine.converters.ProductConverter;
import com.gerps.magazine.dto.ProductDto;
import com.gerps.magazine.entity.Product;
import com.gerps.magazine.exceptions.ErrorDetails;
import com.gerps.magazine.exceptions.ResponseDetails;
import com.gerps.magazine.services.ProductsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by Grzesiek on 2018-11-18
 */

@RestController
@RequestMapping("magazin")
public class ProductsRestController {

    private static final Logger logger = LoggerFactory.getLogger(ProductsRestController.class);
    private ProductsService productsService;

    @Autowired
    private ProductConverter productConverter;

    @Autowired
    public ProductsRestController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @GetMapping("/products/all")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<ProductDto> findAllProducts() {
        logger.info("Rest findAllProducts()");
        return productsService.findAllProducts();
    }

    @GetMapping("/products/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<ProductDto> findProductById(@PathVariable Long id) {
        logger.info("Rest findProductById={}", id);

        ProductDto productDto = productsService.findProductById(id);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    @PostMapping("/products/add")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Object> addProduct(@RequestBody ProductDto productDto) {
        logger.info("add to db new product {}", productDto.getName());

        if (productDto != null) {
            Product product = productConverter.apply(productDto);
            productsService.save(product);

            ResponseDetails details = new ResponseDetails(new Date(), "Product " + productDto.getName() + " add to db.");
            return new ResponseEntity(product, HttpStatus.CREATED);
        } else {
            logger.error("Body is empty");
            ResponseDetails details = new ResponseDetails(new Date(), "POST is empty");
            return new ResponseEntity(details, HttpStatus.BAD_REQUEST);
        }

    }

    //do aktualizowania poszczegolnych pol productu
    @PatchMapping("/products/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public void editProductById(@PathVariable Long id) {

    }


}