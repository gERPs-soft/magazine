package com.gerps.magazine.services.impl;

import com.gerps.magazine.converters.ProductConverter;
import com.gerps.magazine.converters.ProductDtoConverter;
import com.gerps.magazine.dto.ProductDto;
import com.gerps.magazine.entity.Product;
import com.gerps.magazine.exceptions.EntityNotFoundException;
import com.gerps.magazine.repository.ProductsRepository;
import com.gerps.magazine.services.ProductsService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Grzesiek on 2018-11-18
 */

@Service
public class ProductsServiceImpl implements ProductsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductsServiceImpl.class);
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
    @Cacheable(value = "ProdCache")
    public List<ProductDto> findAllProducts() throws EntityNotFoundException {

        //Iterable<Product> products = productsRepository.findAll();
        Iterable<Product> products = productsRepository.findAllByActiveTrue();

        if (products != null) {
            LOGGER.info("Found {} products in DB", ((List<Product>) products).size());
            List<ProductDto> productDtos = new ArrayList<>();
            products.forEach(product ->
                    productDtos.add(productDtoConverter.apply(product)));

            return productDtos;
        } else {
            LOGGER.info("Products list is empty. Not found any products");
            throw new EntityNotFoundException("Products list is empty. No products found in database. Please try again later.");
        }
    }

    @Override
    @Cacheable(value = "ProdCache")
    public ProductDto findProductById(Long id) throws EntityNotFoundException {

        Optional<Product> optionalProduct = productsRepository.findById(id);

        if (optionalProduct.isPresent()) {
            Product productIsPresent = optionalProduct.get();
            LOGGER.info("Found product {} in DB", productIsPresent.getName());
            return productDtoConverter.apply(productIsPresent);
        } else {
            LOGGER.info("Not found product by id: {}", id);
            throw new EntityNotFoundException("Product with id " + id + " was not found in database. Please try again with another id.");
        }
    }

    @Override
    public void save(Product product) {
        LOGGER.info("Save to db product with name={}", product.getName());
        productsRepository.save(product);
    }

    @Override
    public void delete(Long id) {
        LOGGER.info("Delete product id={}", id);
        Product productToDelete = productsRepository.findById(id).get();
        productToDelete.setActive(false);
        productsRepository.save(productToDelete);
    }
}
