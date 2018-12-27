package com.gerps.magazine.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gerps.magazine.converters.ProductConverter;
import com.gerps.magazine.converters.ProductDtoConverter;
import com.gerps.magazine.dto.ProductDto;
import com.gerps.magazine.dto.SupplierDto;
import com.gerps.magazine.entity.Product;
import com.gerps.magazine.services.ProductsGroupService;
import com.gerps.magazine.services.ProductsService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Grzesiek on 2018-11-20
 */

@RunWith(SpringRunner.class)
@WebMvcTest(ProductsRestController.class)
public class ProductsRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductsService productsService;

    @MockBean
    private ProductConverter productConverter;

    @MockBean
    private ProductsGroupService productsGroupService;

    private JacksonTester<ProductDto> jsonProductDto;

    @Before
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    public void shouldReturnProductByIdWhenExists() throws Exception {

        ProductDto testProductDto = new ProductDto(1L, "1.02.003", "Reczniki PREMIUM 4000 biale", 2, "szt", "112200445511", 8.2, "Karton", 50, 40, 60, 32, 1l, 320, BigDecimal.TEN, "VAT_23");

        given(productsService.findProductById(1L)).willReturn(testProductDto);

        mockMvc.perform(get("/magazine/products/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Reczniki PREMIUM 4000 biale")));
    }

    @Test
    public void findProductByName() throws Exception{
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "http://localhost:8080/magazine/products/1";
        ResponseEntity<String> response
                = restTemplate.getForEntity(resourceUrl, String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        JsonNode value = root.findValue("name");
        //assertThat(name.findValues("name"), notNullValue());
        Assert.assertEquals("\"Reczniki PREMIUM 4000 biale\"", value.toString());
    }

    @Test
    public void addProduct() {
    }

    /*@Configuration
    @EnableWebMvc
    public static class TestConfiguration {

        private ProductsService productsService;
        private ProductConverter productConverter;
        private ProductsGroupService productsGroupService;

        @Bean
        public ProductsRestController productsRestController() {
            return new ProductsRestController(productsService, productConverter, productsGroupService);
        }

    }*/
}