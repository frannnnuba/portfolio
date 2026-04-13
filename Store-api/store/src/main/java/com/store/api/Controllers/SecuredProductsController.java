package com.store.api.Controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.store.api.DTOs.ProductDTO;
import com.store.api.Service.SecuredProductsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;





@RestController
@RequestMapping("/api/products/secured/")
public class SecuredProductsController {
    @Autowired 
    private SecuredProductsService securedService;
    //security implementation will be introduced later
    //as for now will be just methods 

    @PostMapping("add_product")
    //PreAuthorize(hasRole('ADMIN'))
    public ProductDTO addProductToCatalogue(@RequestBody ProductDTO productDTO) {
        return securedService.addProductToCatalogue(productDTO);
    }

    @PutMapping("path/{id}")
    //PreAuthorize(hasRole('ADMIN'))
    public ProductDTO modifyProduct(@PathVariable Long id,@RequestBody ProductDTO aDTO) {
       return securedService.modifyProduct(id, aDTO);
    }

    @PatchMapping("/patch_product/{id}")
    //PreAuthorize(hasRole('ADMIN'))
    public ProductDTO patchProduct(@PathVariable Long id,@RequestBody Map<String,Object> updates){
        return securedService.patchProduct(id, updates);
    }
    
}
