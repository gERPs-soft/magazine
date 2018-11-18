package com.gerps.magazine.services;

import com.gerps.magazine.dto.ProductDto;
import com.gerps.magazine.entity.Product;

import java.util.List;
import java.util.Optional;

/**
 * Created by Grzesiek on 2018-11-18
 */

public interface ProductsService {

    List<ProductDto> findAllProducts();

    ProductDto findProductById(Long id);

    void save(Product product);

    void delete(Product product);
}
