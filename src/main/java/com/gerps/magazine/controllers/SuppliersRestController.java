package com.gerps.magazine.controllers;

import com.gerps.magazine.converters.SupplierDtoConverter;
import com.gerps.magazine.dto.ResponseDetails;
import com.gerps.magazine.dto.SupplierDto;
import com.gerps.magazine.entity.Supplier;
import com.gerps.magazine.services.SupplierService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by Grzesiek on 2018-11-18
 */

@RestController
@RequestMapping("magazine/suppliers")
@CrossOrigin(origins = "http://localhost:4200")
public class SuppliersRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SuppliersRestController.class);
    private SupplierService supplierService;
    private SupplierDtoConverter supplierDtoConverter;

    @Autowired
    public SuppliersRestController(SupplierService supplierService, SupplierDtoConverter supplierDtoConverter) {
        this.supplierService = supplierService;
        this.supplierDtoConverter = supplierDtoConverter;
    }

    @GetMapping("/all")
    public List<SupplierDto> findAllSuppliers() {
        LOGGER.info("Rest findAllSuppliers()");

        return supplierService.findAllSuppliers();
    }

    @GetMapping("/{id}")
    public SupplierDto findSupplierById(@PathVariable Long id) {
        LOGGER.info("findSupplierById={}", id);

        return supplierService.findSupplierById(id);
    }

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addSupplier(@RequestBody Supplier supplier) {

        ResponseDetails details;
        Long supplierId = supplier.getId();

        if (supplier != null) {

            if (supplierId == null) {
                LOGGER.info("Save new supplier {}", supplier.getName());
                details = new ResponseDetails(new Date(), "Supplier " + supplier.getName() + " add to db.");

                supplierService.save(supplier);

            } else if (supplierService.findSupplierById(supplierId) != null) {
                LOGGER.info("Edit supplier id={}", supplierId);
                details = new ResponseDetails(new Date(), "Supplier id=" + supplierId + " edit in db.");

                supplierService.save(supplier);
            } else {
                LOGGER.info("Product id={} not found in db!", supplierId);
                details = new ResponseDetails(new Date(), "Product " + supplierId + " is not found in db. Please try with another id.");
            }
            return new ResponseEntity(details, HttpStatus.CREATED);

        } else {
            LOGGER.info("RequestBody is empty");
            details = new ResponseDetails(new Date(), "Body is empty");
            return new ResponseEntity(details, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping("/delete/{id}")
    public ResponseEntity deleteSupplier(@PathVariable Long id) {
        supplierService.deleteById(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
