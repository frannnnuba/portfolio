package com.store.api.Handler;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.store.api.BussinesExceptions.EmptyCartException;
import com.store.api.BussinesExceptions.OutOfStockException;
import com.store.api.Controllers.CartController;
import com.store.api.Filter.JWTAuthFilter;
import com.store.api.Service.CartService;
import com.store.api.Service.CheckoutService;



@WebMvcTest(controllers = CartController.class,
    excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = JWTAuthFilter.class
))
@AutoConfigureMockMvc(addFilters = false)
public class HandlerForBussinesExceptionsTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CheckoutService checkout_serv;

    //had to do this because the current spring boot vsn doesn't support @MockBean

    @TestConfiguration
    static class TestConfig {

        @Bean
        public CheckoutService checkoutService() {
            return Mockito.mock(CheckoutService.class);
        }

        @Bean
        public CartService cartService() {
            return Mockito.mock(CartService.class);
        }
    }

    @Test
    void testOutOfStockHandler()throws Exception{
        when(checkout_serv.checkout(1L)).thenThrow(new OutOfStockException("Not enough stock"));
        
        mockMvc.perform(post("/api/cart/checkout/1")).
        andExpect(status().isConflict()).andExpect(jsonPath("$.msg").
        value("Not enough stock"));
    }

    @Test
    void testEmptyCartHandler() throws Exception{
        String error = "Empty cart can't be checked out";
         when(checkout_serv.checkout(2L)).thenThrow(new EmptyCartException(error));

        mockMvc.perform(post("/api/cart/checkout/2")).
        andExpect(status().isBadRequest()).andExpect(jsonPath("$.msg").
        value(error));      
    }
}
