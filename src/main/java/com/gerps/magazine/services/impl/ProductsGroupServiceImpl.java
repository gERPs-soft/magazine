package com.gerps.magazine.services.impl;

import com.gerps.magazine.entity.ProductGroup;
import com.gerps.magazine.exceptions.EntityNotFoundException;
import com.gerps.magazine.repository.ProductsGroupRepository;
import com.gerps.magazine.services.ProductsGroupService;
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
 * Created by Grzesiek on 2018-11-24
 */

@Service
public class ProductsGroupServiceImpl implements ProductsGroupService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductsGroupServiceImpl.class);
    private ProductsGroupRepository productsGroupRepository;

    @Autowired
    public ProductsGroupServiceImpl(ProductsGroupRepository productsGroupRepository) {
        this.productsGroupRepository = productsGroupRepository;
    }

    @Override
    @Cacheable(value = "GroupsCache")
    public List<ProductGroup> findAllProductsGroup() throws EntityNotFoundException {

        Iterable<ProductGroup> productsGroups = productsGroupRepository.findAll();

        if (productsGroups != null) {
            List<ProductGroup> productsGroupList = new ArrayList<>();
            productsGroups.forEach(pg -> productsGroupList.add(pg));
            LOGGER.info("Found {} products group in Db", productsGroupList.size());
            return productsGroupList;
        } else {
            LOGGER.error("Product group list is empty. Not found any product group");
            throw new EntityNotFoundException("Product group not found in database. Please try again later.");
        }
    }

    @Override
    public ProductGroup findProductsGroupById(Integer id) throws EntityNotFoundException {

        Optional<ProductGroup> groupOptional = productsGroupRepository.findById(id);

        if (groupOptional.isPresent()) {
            ProductGroup productGroup = groupOptional.get();
            LOGGER.info("Found products group {}", productGroup.getName());
            return productGroup;
        } else {
            LOGGER.error("Product group orderId={} not found", id);
            throw new EntityNotFoundException("Product group by orderId=" + id + " not found in database. Please try again later.");
        }
    }
}
