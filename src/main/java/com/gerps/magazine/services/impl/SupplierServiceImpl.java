package com.gerps.magazine.services.impl;

import com.gerps.magazine.dto.SupplierDto;
import com.gerps.magazine.entity.Supplier;
import com.gerps.magazine.services.SupplierService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Created by Grzesiek on 2018-11-18
 */

@Service
public class SupplierServiceImpl implements SupplierService {

    @Override
    public List<SupplierDto> findAllSuppliers() {
        return Collections.emptyList();
    }

    @Override
    public Supplier findSupplierById() {
        return null;
    }
}
