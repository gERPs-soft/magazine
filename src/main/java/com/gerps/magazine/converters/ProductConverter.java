package com.gerps.magazine.converters;

import com.gerps.magazine.dto.ProductDto;
import com.gerps.magazine.entity.*;
import com.gerps.magazine.repository.SuppliersRepository;
import com.gerps.magazine.services.ProductsGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Function;

/**
 * Created by Grzesiek on 2018-11-18
 */

@Component
public class ProductConverter implements Function<ProductDto, Product> {

    private final static Logger LOGGER = LoggerFactory.getLogger(ProductConverter.class);

    private ProductsGroupService productsGroupService;
    private SuppliersRepository suppliersRepository;

    @Autowired
    public ProductConverter(ProductsGroupService productsGroupService, SuppliersRepository suppliersRepository) {
        this.productsGroupService = productsGroupService;
        this.suppliersRepository = suppliersRepository;
    }

    @Override
    public Product apply(ProductDto productDto) {
        LOGGER.info("ProductConverter.apply()");

        ProductGroup productsGroup = productsGroupService.findProductsGroupById(productDto.getProduct_group());
        Optional<Supplier> optionalSupplier = suppliersRepository.findById(productDto.getSupplier());
        Supplier supplierObject = optionalSupplier.get();

        Product product = new Product();
        product.setId(productDto.getId());
        product.setAssort_index(productDto.getAssort_index());
        product.setName(productDto.getName());
        product.setProduct_group(productsGroup);
        product.setUnitOfMasure(UnitOfMasure.valueOf(productDto.getUnitOfMasure()));
        product.setBarcode(productDto.getBarcode());
        product.setWeight_unit(productDto.getWeight_unit());
        product.setPackageUnit(PackageUnit.valueOf(productDto.getPackage_unit()));
        product.setNumber_in_package(productDto.getNumber_in_package());
        product.setHeight(productDto.getHeight());
        product.setWeight(productDto.getWeight());
        product.setLength(productDto.getLength());
        product.setPrice(productDto.getPrice());
        product.setSupplier(supplierObject);
        product.setStock(productDto.getStock());
        product.setVat(Vat.valueOf(String.valueOf(productDto.getVat())));

        //narazie nieistniejace w dto
        product.setNumber_in_pallet(1);

        return product;
    }
}