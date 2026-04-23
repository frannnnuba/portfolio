package com.store.api.Handler;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.store.api.BussinesExceptions.ProductNotFoundException;
import com.store.api.Controllers.ReadOnlyProductsController;
import com.store.api.Filter.JWTAuthFilter;
import com.store.api.Repository.ProductsRepository;
import com.store.api.Service.ReadProductsService;

@WebMvcTest(controllers = ReadOnlyProductsController.class,
    excludeFilters = {
        @ComponentScan.Filter(
            type = FilterType.ASSIGNABLE_TYPE,
            classes = JWTAuthFilter.class
        )
})
@Import(HandlerForProductExceptions.TestConfig.class)
@AutoConfigureMockMvc(addFilters = false)
public class HandlerForProductExceptions {
    @Autowired
    private MockMvc mockMvc; 
    
    @Autowired
    private ReadProductsService rprod_serv;

    //@Autowired
    //private ProductsRepository prod_repository;

    @TestConfiguration
    static class TestConfig {
        @Bean
        @Primary
        public ReadProductsService readProductsService(){
            return Mockito.mock(ReadProductsService.class);
        }

        @Bean
        public ProductsRepository productsRepository(){
            return Mockito.mock(ProductsRepository.class);
        }
    }

    @Test
    void testProductNotFoundHandler() throws Exception {
        when(rprod_serv.findById(1L)).thenThrow(new ProductNotFoundException("Product not found"));
        
        mockMvc.perform(get("/api/products/public/findBy/1")).
        andExpect(status().isNotFound()).
        andExpect(jsonPath("$.msg").value("Product not found"));
    }

}
