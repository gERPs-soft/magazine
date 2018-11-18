package com.gerps.magazine.services;

import com.gerps.magazine.dto.SupplierDto;
import com.gerps.magazine.entity.Supplier;
import com.gerps.magazine.exceptions.EntityNotFoundException;

import java.util.List;

/**
 * Created by Grzesiek on 2018-11-18
 */
public interface SupplierService {

    List<SupplierDto> findAllSuppliers() throws EntityNotFoundException;
    SupplierDto findSupplierById(Long id) throws EntityNotFoundException;
    void save(Supplier supplier);
    void deleteById(Long id) throws EntityNotFoundException;
}
