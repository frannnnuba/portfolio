package com.store.api.Controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.store.api.DTOs.ProductDTO;
import com.store.api.Service.ReadProductsService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("api/products/public")
public class ReadOnlyProductsController {

    @Autowired
    private ReadProductsService readService;

    @GetMapping("/list_all")
    public Set<?> listAllProducts(String param) {
        return readService.findAll();
    }
    
    @GetMapping("/{aCategory}")
    public Set<ProductDTO> findByCategory (@PathVariable String aCategory) {
        return readService.findByCategory(aCategory);
    }
    
    @GetMapping("/price_of/{a_product_name}")
    public Double priceOf(@PathVariable String a_product_name) {
        return readService.priceOf(a_product_name);
    }
    
}
