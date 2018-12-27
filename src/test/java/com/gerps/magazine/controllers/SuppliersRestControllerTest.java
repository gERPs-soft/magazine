
package com.gerps.magazine.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.gerps.magazine.converters.SupplierDtoConverter;
import com.gerps.magazine.dto.SupplierDto;
import com.gerps.magazine.exceptions.EntityNotFoundException;
import com.gerps.magazine.services.SupplierService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Grzesiek on 2018-12-20
 */


/*
@RunWith(MockitoJUnitRunner.class)
public class SuppliersRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @Mock
    private SupplierService supplierService;

    @Mock
    private SupplierDtoConverter supplierDtoConverter;

    @InjectMocks
    private SuppliersRestController controller = new SuppliersRestController(supplierService, supplierDtoConverter);

    @Before
    public void setUp() throws Exception {
        this.mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void shouldReturnSupplierById() throws Exception {

        SupplierDto supplier = createSupplier();
        //when(supplierService.findSupplierById(1l)).thenReturn(supplier);

        given(supplierService.findSupplierById(1l)).willReturn(supplier);

        System.out.println(supplier.getName());
        System.out.println(supplier.getId());

        mvc.perform(get("/magazine/suppliers/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(supplier.getName())));
    }

    private SupplierDto createSupplier() {
        return new SupplierDto(1l, "Flesz Sp. z o.o.", "11800318224", "Warszawa", "Cieślewskich", "25F", "03-017", "224238888", "zamowienia@flesz.net.pl", "www.flesz.net.pl", "Maciej Russek", "Santander Bank", "10204000001234885511");
    }
}*/


@RunWith(SpringRunner.class)
@WebMvcTest(SuppliersRestController.class)
public class SuppliersRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private SupplierService supplierService;

    @MockBean
    private SupplierDtoConverter supplierDtoConverter;

    private JacksonTester<SupplierDto> jsonSupplierDto;

    @Before
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    public void shouldReturnAllSuppliers() throws Exception {
        SupplierDto supplierDto1 = createSupplier();
        SupplierDto supplierDto2 = createSupplier();
        SupplierDto supplierDto3 = createSupplier();

        List<SupplierDto> allSuppliers = Arrays.asList(supplierDto1, supplierDto2, supplierDto3);

        given(supplierService.findAllSuppliers()).willReturn(allSuppliers);

        mvc.perform(get("/magazine/suppliers/all")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(3)));
    }

    @Test
    public void shouldReturnSupplierByIdWhenExists() throws Exception {

        SupplierDto supplier = createSupplier();

        given(supplierService.findSupplierById(1l)).willReturn(supplier);

        /*MockHttpServletResponse response = */
        mvc.perform(
                get("/magazine/suppliers/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").isNotEmpty());
        //.andReturn().getResponse();

        /*assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonSupplierDto.write(createSupplier()).getJson());
        assertThat(response.getContentAsString());*/
    }

    @Test
    public void shouldReturnStatusNotFoundWhenSupplierByIdNotExists() throws Exception {

        given(supplierService.findSupplierById(100l)).willThrow(EntityNotFoundException.class);

        mvc.perform(
                get("/magazine/suppliers/100")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    private SupplierDto createSupplier() {
        return new SupplierDto(1l, "Flesz Sp. z o.o.", "11800318224", "Warszawa", "Cieślewskich", "25F", "03-017", "224238888", "zamowienia@flesz.net.pl", "www.flesz.net.pl", "Maciej Russek", "Santander Bank", "10204000001234885511");
    }
}