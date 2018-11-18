package com.gerps.magazine.services;

import com.gerps.magazine.entity.Product;

import java.util.List;
import java.util.Optional;

/**
 * Created by Grzesiek on 2018-11-18
 */

public interface ProductsService {

    List<Product> findAllProducts();

    Optional<Product> findProductById(Long id);

    void save(Product product);

    void delete(Product product);
}
