package com.store.api.Controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.store.api.Service.ReadProductsService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("api/products/public")
public class ReadOnlyProductsController {

    @Autowired
    private ReadProductsService readService;

    /*@GetMapping
    public Set<?> listAllProducts(@RequestParam String param) {
        return new Set<DTOProduct>();
    }*/
    

}
