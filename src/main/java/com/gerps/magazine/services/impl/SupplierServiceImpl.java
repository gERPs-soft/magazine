package com.gerps.magazine.services.impl;

import com.gerps.magazine.converters.SupplierDtoConverter;
import com.gerps.magazine.dto.SupplierDto;
import com.gerps.magazine.entity.Supplier;
import com.gerps.magazine.exceptions.EntityNotFoundException;
import com.gerps.magazine.repository.SuppliersRepository;
import com.gerps.magazine.services.SupplierService;
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
public class SupplierServiceImpl implements SupplierService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SupplierServiceImpl.class);
    private SuppliersRepository suppliersRepository;
    private SupplierDtoConverter supplierDtoConverter;

    @Autowired
    public SupplierServiceImpl(SuppliersRepository suppliersRepository, SupplierDtoConverter supplierDtoConverter) {
        this.suppliersRepository = suppliersRepository;
        this.supplierDtoConverter = supplierDtoConverter;
    }

    @Override
    @Cacheable(value = "SuppliersCache")
    public List<SupplierDto> findAllSuppliers() throws EntityNotFoundException {
        //Iterable<Supplier> supplierIterable = suppliersRepository.findAll();
        Iterable<Supplier> supplierIterable = suppliersRepository.findAllByActiveTrue();

        if (!((List<Supplier>) supplierIterable).isEmpty()) {
            List<SupplierDto> supplierDtoList = new ArrayList<>();
            supplierIterable.forEach(supplier ->
                    supplierDtoList.add(supplierDtoConverter.apply(supplier)));

            LOGGER.info("Found {} suppliers in Db", supplierDtoList.size());
            return supplierDtoList;
        } else {
            LOGGER.info("Suppliers list is empty. Not found any suppliers");
            throw new EntityNotFoundException("Suppliers list is empty. No suppliers found in database. Please try again later.");
        }
    }

    @Override
    @Cacheable(value = "SuppliersCache")
    public SupplierDto findSupplierById(Long id) throws EntityNotFoundException {

        Optional<Supplier> optionalSupplier = suppliersRepository.findById(id);

        if (optionalSupplier.isPresent()) {
            LOGGER.info("findSupplierById={} in Db", id);
            return supplierDtoConverter.apply(optionalSupplier.get());
        } else {
            throw new EntityNotFoundException("Supplier with orderId=" + id + " was not found in database. Please try again with another orderId");
        }
    }

    @Override
    public void save(Supplier supplier) {
        LOGGER.info("Save to db supplier {}", supplier.getName());
        suppliersRepository.save(supplier);
    }

    @Override
    public void deleteById(Long id) throws EntityNotFoundException {
        Supplier supplier = suppliersRepository.findById(id).get();
        LOGGER.info("Deleted supplier id={} name={}", id, supplier.getName());
        supplier.setActive(false);
        suppliersRepository.save(supplier);
    }
}

//@todo poczytaj o tranzakcyjnosci i poziomach izolacji tranzakcji