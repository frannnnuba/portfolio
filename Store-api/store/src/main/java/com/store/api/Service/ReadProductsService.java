package com.store.api.Service;


import java.util.Set;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.api.DTOs.ProductDTO;
import com.store.api.Mappers.MapperProduct;
import com.store.api.Repository.ProductsRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ReadProductsService {

    @Autowired
    private ProductsRepository products_repository;

    public Set<ProductDTO> findAll(){
        return products_repository.findAll().stream().
        map(MapperProduct::toDto).collect(Collectors.toSet());
    }
    
    public Set<ProductDTO> findByCategory(String aCategory){
        Set<ProductDTO> aSetOfCategory = products_repository.findByCategory(aCategory).
        stream().map(MapperProduct::toDto).collect(Collectors.toSet());
        return aSetOfCategory;
    }

    public Double priceOf(String item_name){
       return  products_repository.findByProductName(item_name).
        map(MapperProduct::toDto).map(ProductDTO::getPrice).
        orElseThrow(()-> new EntityNotFoundException());
    }

    public Double priceById(Long id){
       return  products_repository.findById(id).
        map(MapperProduct::toDto).map(ProductDTO::getPrice).
        orElseThrow(()-> new EntityNotFoundException());
    }
}
