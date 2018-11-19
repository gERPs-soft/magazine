package com.gerps.magazine.controllers;

import com.gerps.magazine.dto.SupplierDto;
import com.gerps.magazine.services.SupplierService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Grzesiek on 2018-11-18
 */

@RestController
@RequestMapping("magazin/suppliers")
public class SuppliersRestController {

    private static final Logger logger = LoggerFactory.getLogger(SuppliersRestController.class);
    private SupplierService supplierService;

    @Autowired
    public SuppliersRestController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping("/all")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<SupplierDto> findAllSuppliers() {
        logger.info("Rest findAllSuppliers()");
        return supplierService.findAllSuppliers();
    }

    @GetMapping("/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    @ResponseBody
    public SupplierDto findSupplierById(@PathVariable Long id) {
        logger.info("findSupplierById={}", id);

        return supplierService.findSupplierById(id);
    }

    /*@RequestMapping("/example")
    @ResponseBody
    public String getExample(HttpServletRequest request) {
        String ipadress = request.getRemoteAddr();
        String browser = request.getHeader("User-Agent");
        String uri = request.getRequestURI();

        return "Your ip:" + ipadress + System.lineSeparator() + "Your browser: " + browser + System.lineSeparator() + "Uri: " + uri;
    }*/
}
