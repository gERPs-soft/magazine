package com.gerps.magazine.converters;

import com.gerps.magazine.dto.SupplierDto;
import com.gerps.magazine.entity.Supplier;
import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * Created by Grzesiek on 2018-11-18
 */

@Component
public class SupplierDtoConverter implements Function<Supplier, SupplierDto> {

    @Override
    public SupplierDto apply(Supplier supplier) {

        return new SupplierDto(supplier.getId(), supplier.getName(), supplier.getCity(), supplier.getStreet(), supplier.getStreet_number(), supplier.getPost_code(), supplier.getPhone_number(), supplier.getEmail(),
                supplier.getWww(), supplier.getRepresentative_person(), supplier.getBank_supplier_name(), supplier.getBank_supplier_account_number());
    }
}
