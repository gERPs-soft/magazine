package com.gerps.magazine.services;

import com.gerps.magazine.dto.SupplierDto;
import com.gerps.magazine.entity.Supplier;

import java.util.List;

/**
 * Created by Grzesiek on 2018-11-18
 */
public interface SupplierService {

    List<SupplierDto> findAllSuppliers();
    Supplier findSupplierById();
}
