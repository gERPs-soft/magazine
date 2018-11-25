package com.gerps.magazine.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gerps.magazine.converters.ProductConverter;
import com.gerps.magazine.converters.ProductDtoConverter;
import com.gerps.magazine.dto.ProductDto;
import com.gerps.magazine.entity.Product;
import com.gerps.magazine.services.ProductsService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Grzesiek on 2018-11-20
 */

@RunWith(SpringRunner.class)
//@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(ProductsRestController.class)
public class ProductsRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

   @MockBean
    private ProductDtoConverter productDtoConverter;

    @MockBean
    private ProductConverter productConverter;

    @MockBean
    private ProductsService productsService;

    /*@Test
    public void findProductById() throws Exception {
        //Product testProduct = new Product("Papier PROFIT Flesz 2w bialy");
        //ProductDto testProductDto = productDtoConverter.apply(testProduct);

        //List<ProductDto> allProducts = Arrays.asList(testProductDto);

        //given(productsService.findProductById(2l)).willReturn(testProductDto);

        mockMvc.perform(get("/magazine/products/2")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                *//*.andExpect(jsonPath("id").value(2))
                .andExpect(jsonPath("name", is("Papier PROFIT Flesz 2w bialy")))*//*
        ;
    }*/

    @Test
    public void findProductByName() throws Exception{
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "http://localhost:8080/magazine/products/1";
        ResponseEntity<String> response
                = restTemplate.getForEntity(resourceUrl, String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        //JsonNode name = root.path("name");
        JsonNode value = root.findValue("name");
        //assertThat(name.findValues("name"), notNullValue());
        Assert.assertEquals("\"R?czniki PREMIUM 4000 biale\"", value.toString());
    }

    @Test
    public void addProduct() {
    }

    @Configuration
    @EnableWebMvc
    public static class TestConfiguration {

        private ProductsService productsService;
        private ProductConverter productConverter;

        @Bean
        public ProductsRestController productsRestController() {
            return new ProductsRestController(productsService, productConverter);
        }

    }
}