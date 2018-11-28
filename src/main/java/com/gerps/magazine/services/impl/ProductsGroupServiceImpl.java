package com.gerps.magazine.services.impl;

import com.gerps.magazine.entity.ProductGroup;
import com.gerps.magazine.exceptions.EntityNotFoundException;
import com.gerps.magazine.repository.ProductsGroupRepository;
import com.gerps.magazine.services.ProductsGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Grzesiek on 2018-11-24
 */

@Service
public class ProductsGroupServiceImpl implements ProductsGroupService {

    private static final Logger logger = LoggerFactory.getLogger(ProductsGroupServiceImpl.class);
    private ProductsGroupRepository productsGroupRepository;

    @Autowired
    public ProductsGroupServiceImpl(ProductsGroupRepository productsGroupRepository) {
        this.productsGroupRepository = productsGroupRepository;
    }

    @Override
    public List<ProductGroup> findAllProductsGroup() throws EntityNotFoundException {

        Iterable<ProductGroup> productGroupsIterable = productsGroupRepository.findAll();

        if(productGroupsIterable!=null){
            List<ProductGroup> productsGroupList = new ArrayList<>();
            productGroupsIterable.forEach(pg -> productsGroupList.add(pg));
            logger.info("Found {} products group.", productsGroupList.size());
            return productsGroupList;
        }else {
            logger.error("Product group list is empty. Not found any product group");
            throw new EntityNotFoundException("Product group not found in database. Please try again later.");
        }
    }

    @Override
    public ProductGroup findProductsGroupById(Integer id) throws EntityNotFoundException {

        Optional<ProductGroup> groupOptional = productsGroupRepository.findById(id);

        if (groupOptional.isPresent()) {
            ProductGroup productGroup = groupOptional.get();
            logger.info("Found products group orderId={} name={}", id, productGroup.getName());
            return productGroup;
        }else{
            logger.error("Product group orderId={} not found", id);
            throw new EntityNotFoundException("Product group by orderId="+id+" not found in database. Please try again later.");
        }
    }
}
