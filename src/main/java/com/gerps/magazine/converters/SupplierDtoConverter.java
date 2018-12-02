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

    // RW: avoid naming like 's' or similar.
    @Override
    public SupplierDto apply(Supplier s) {

        return new SupplierDto(s.getId(), s.getName(), s.getCity(), s.getStreet(), s.getStreet_number(), s.getPost_code(), s.getPhone_number(), s.getEmail(),
                s.getWww(), s.getRepresentative_person(), s.getBank_supplier_name(), s.getBank_supplier_account_number());
    }
}
