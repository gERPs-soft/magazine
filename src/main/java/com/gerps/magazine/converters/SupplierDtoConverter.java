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
        return null;
    }
}
