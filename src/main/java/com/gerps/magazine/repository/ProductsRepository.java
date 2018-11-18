package com.gerps.magazine.repository;

import com.gerps.magazine.entity.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * Created by Grzesiek on 2018-11-18
 */

@Repository
@CrossOrigin(origins = "http://localhost:4200")
public interface ProductsRepository extends CrudRepository<Product, Long> {
}
