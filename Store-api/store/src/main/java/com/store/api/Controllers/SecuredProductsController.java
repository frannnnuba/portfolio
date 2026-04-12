package com.store.api.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Service.ReadProductsService;

@RestController
@RequestMapping("/api/products/secured/")
public class SecuredProductsController {

    @Autowired
    private ReadProductsService readService;
}
