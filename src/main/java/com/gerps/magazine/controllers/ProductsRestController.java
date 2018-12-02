package com.gerps.magazine.controllers;

import com.gerps.magazine.converters.ProductConverter;
import com.gerps.magazine.dto.MyResponseDetails;
import com.gerps.magazine.dto.ProductDto;
import com.gerps.magazine.entity.Product;
import com.gerps.magazine.entity.ProductGroup;
import com.gerps.magazine.exceptions.EntityNotFoundException;
import com.gerps.magazine.services.ProductsGroupService;
import com.gerps.magazine.services.ProductsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Grzesiek on 2018-11-18
 */

@RestController
@RequestMapping(value = "/magazine/products")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductsRestController {

    private static final Logger logger = LoggerFactory.getLogger(ProductsRestController.class);

    private ProductsService productsService;
    private ProductConverter productConverter;
    private ProductsGroupService productsGroupService;

    @Autowired
    public ProductsRestController(ProductsService productsService, ProductConverter productConverter, ProductsGroupService productsGroupService) {
        this.productsService = productsService;
        this.productConverter = productConverter;
        this.productsGroupService = productsGroupService;
    }


    @GetMapping("/all")
    public List<ProductDto> findAllProducts() {
        logger.info("Rest findAllProducts()");
        return productsService.findAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> findProductById(@PathVariable Long id) {
        logger.info("Rest findProductById={}", id);

        // RW: if product will not be found you can ranturn Optional and rather not throw exception.
        // Exception mainly signals error. It will be better if you only log the fact that product with given ID could not be found.
        // In this case it is better to return 404 (NOT FOUND);
        try {
            ProductDto productDto = productsService.findProductById(id);
            return new ResponseEntity<>(productDto, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.valueOf("Can't find product with id " + id));
    }

    @PostMapping(value = "/add/new", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity addProduct(@RequestBody ProductDto productDto) {
        logger.info("Try add new product {}", productDto.getName());

        ResponseEntity responseEntity;

        // RW: I think you can move conversion to service layer but it is only suggestion.
        if (productDto != null) {
            logger.info("productDto is not empty, save new product {}", productDto.getName());
            Product product = productConverter.apply(productDto);
            productsService.save(product);

            MyResponseDetails details = new MyResponseDetails(new Date(), "Product " + productDto.getName() + " add to db.");
            return new ResponseEntity(details, HttpStatus.CREATED);
        } else {
            logger.error("RequestBody is empty");
            MyResponseDetails details = new MyResponseDetails(new Date(), "Body is empty");
            return new ResponseEntity(details, HttpStatus.BAD_REQUEST);
        }

    }

    // RW: remove dead code.
    //@todo implements controller to edit products
    /*@PostMapping("/products/{orderId}")
    public void editProductById(@PathVariable Long orderId) {

    }*/


    @GetMapping("/products/all-group")
    public List<ProductGroup> findAllProductsGroup() {
        logger.info("Rest findAllProductsGroup()");
        return productsGroupService.findAllProductsGroup();
    }

    // RW: do test like here:
    //https://spring.io/guides/gs/testing-web/

    //metodka testowa wysylajaca POSTem JSONA z nowy produktem
    @GetMapping("/addNewExamplesProduct")
    @CrossOrigin(origins = "http://localhost:8080")
    public void addNewExampleProduct() {
        logger.info("addNewExampleProduct()");

        String jsonToSent = "{" +
                "    \"assort_index\": \"1.07.002\",\n" +
                "    \"name\": \"Papier STANDARD 19 Flesz 1w szary\",\n" +
                "    \"product_group\": 1,\n" +
                "    \"unitOfMasure\": \"szt\",\n" +
                "    \"barcode\": \"112200444413\",\n" +
                "    \"weight_unit\": 4,\n" +
                "    \"package_unit\": \"Karton\",\n" +
                "    \"number_in_package\": 12,\n" +
                "    \"height\": 20,\n" +
                "    \"weight\": 40,\n" +
                "    \"length\": 60,\n" +
                "    \"supplier\": 2,\n" +
                "    \"stock\": 1440,\n" +
                "    \"price\": 1.40,\n" +
                "    \"vat\": \"VAT_23\",\n" +
                "    \"orderId\": null" +
                "}";

        HttpHeaders httpHeaders = new HttpHeaders();
        //httpHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setContentLanguage(Locale.JAPANESE);

        HttpEntity httpEntity = new HttpEntity(jsonToSent, httpHeaders);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> exchange = restTemplate.exchange(
                "http://localhost:8080/magazine/products/add/new",
                HttpMethod.POST,
                httpEntity,
                String.class);
    }


}