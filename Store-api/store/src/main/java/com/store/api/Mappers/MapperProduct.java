package com.store.api.Mappers;

import com.store.api.DTOs.ProductDTO;
import com.store.api.Entity.Product;

public class MapperProduct {

    public static ProductDTO toDto(Product aProduct){
        return new ProductDTO(aProduct.getId(),aProduct.getProductName(),
        aProduct.getBrand_name(),aProduct.getPrice(),aProduct.getStock(),aProduct.getCategory());
    }

    public static Product toEntity(ProductDTO aProductDTO){
        Product newProduct = new Product();
        newProduct.setId(aProductDTO.getId());
        newProduct.setBrand_name(aProductDTO.getBrand_name());
        newProduct.setProductName(aProductDTO.getProduct_name());
        newProduct.setPrice(aProductDTO.getPrice());
        newProduct.setStock(aProductDTO.getStock());
        newProduct.setCategory(aProductDTO.getCategory());
        return newProduct;
    }
}
