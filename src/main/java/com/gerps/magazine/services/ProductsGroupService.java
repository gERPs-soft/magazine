package com.gerps.magazine.services;

import com.gerps.magazine.entity.ProductGroup;
import com.gerps.magazine.exceptions.EntityNotFoundException;

import java.util.List;

/**
 * Created by Grzesiek on 2018-11-24
 */

public interface ProductsGroupService {

    List<ProductGroup> findAllProductsGroup() throws EntityNotFoundException;
    ProductGroup findProductsGroupById(Integer id) throws EntityNotFoundException;

}
