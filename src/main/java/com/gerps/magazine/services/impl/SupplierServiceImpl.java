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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Grzesiek on 2018-11-18
 */

@Service
public class SupplierServiceImpl implements SupplierService {

    private static final Logger logger = LoggerFactory.getLogger(SupplierServiceImpl.class);
    private SuppliersRepository suppliersRepository;
    private SupplierDtoConverter supplierDtoConverter;

    @Autowired
    public SupplierServiceImpl(SuppliersRepository suppliersRepository, SupplierDtoConverter supplierDtoConverter) {
        this.suppliersRepository = suppliersRepository;
        this.supplierDtoConverter = supplierDtoConverter;
    }

    @Override
    public List<SupplierDto> findAllSuppliers() throws EntityNotFoundException {
        logger.info("findAllSuppliers()");

        Iterable<Supplier> supplierIterable = suppliersRepository.findAll();

        if(supplierIterable!=null){
            List<SupplierDto> supplierDtoList = new ArrayList<>();
            supplierIterable.forEach(supplier -> supplierDtoList.add(supplierDtoConverter.apply(supplier)));
            return supplierDtoList;
        }else {
            logger.error("Suppliers list is empty. Not found any suppliers");
            throw new EntityNotFoundException("Suppliers list is empty. No suppliers found in database. Please try again later.");
        }
    }

    @Override
    public SupplierDto findSupplierById(Long id) throws EntityNotFoundException {
        logger.info("findSupplierById={}", id);
        Optional<Supplier> optionalSupplier = suppliersRepository.findById(id);

        if (optionalSupplier.isPresent()) {
            SupplierDto supplierDto = supplierDtoConverter.apply(optionalSupplier.get());
            return supplierDto;
        } else {
            throw new EntityNotFoundException("Supplier with id="+id+" was not found in database. Please try again with another id");
        }
    }

    @Override
    public void save(Supplier supplier) {

    }

    @Override
    public void deleteById(Long id) throws EntityNotFoundException {

    }
}
