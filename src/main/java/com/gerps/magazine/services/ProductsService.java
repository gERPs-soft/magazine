package com.gerps.magazine.services;

import com.gerps.magazine.dto.ProductDto;
import com.gerps.magazine.entity.Product;
import com.gerps.magazine.exceptions.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

/**
 * Created by Grzesiek on 2018-11-18
 */

public interface ProductsService {

    List<ProductDto> findAllProducts() throws EntityNotFoundException;

    ProductDto findProductById(Long id) throws EntityNotFoundException;

    void save(Product product);

    void delete(Long id);
}
