package com.gerps.magazine.repository;

import com.gerps.magazine.dto.ProductDto;
import com.gerps.magazine.entity.Product;
import com.gerps.magazine.services.ProductsService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.metamodel.EntityType;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Created by Grzesiek on 2018-11-20
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductsRepositoryTest {

    @Autowired
    private ProductsService productsService;

    @Test
    public void shouldFindProductById(){
        ProductDto product = productsService.findProductById(2l);
        Assert.assertNotNull(product);
    }

    /*@Test
    public void shouldFindProductIsWrongId(){
        ProductDto productNull = productsService.findProductById(20l);
        Assert.assertEquals("", productNull.getName());
    }*/

    @Test
    public void shouldFindAllProductsReturn2Products(){
        List<ProductDto> productDtoList = productsService.findAllProducts();
        Assert.assertEquals(4, productDtoList.size());
    }
}