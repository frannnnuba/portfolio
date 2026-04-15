package com.store.api.Entity;

import com.store.api.DTOs.ProductDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name= "product_name",nullable = false)
    private String productName;

    @Column(name = "brand_name",nullable = false)
    private String brand_name;

    @Column(name = "price",nullable = false)
    private Double price;

    @Column(name = "stock",nullable = false)
    private Long stock;

    @Column(nullable = false)
    private String category;

     public void modifyProduct(ProductDTO aDTO){
        if(aDTO.getPrice()<= 0){
            throw new IllegalArgumentException("Price must be over 0.00");
        }
        if(aDTO.getStock() <=0){
            throw new IllegalArgumentException("Stock must be over 0");
        }
        setProductName(aDTO.getProduct_name());
        setBrand_name(aDTO.getBrand_name());
        setPrice(aDTO.getPrice());
        setStock(aDTO.getStock());
        setCategory(aDTO.getCategory());
     }
    
    public void changeProductName(String name){
        if(name.isBlank() || name.isEmpty()){
            throw new IllegalArgumentException(); 
        }
        setProductName(name);
    }

    public void changeBrandName(String name){
        if(name.isBlank() || name.isEmpty()){
            throw new IllegalArgumentException(); 
        }
        setBrand_name(name);
    }

    public void changePrice(Double price){
        if(price<= 0){
            throw new IllegalArgumentException("Price must be over 0.00");
        }
        setPrice(price);
    }

    public void changeStock(Long stock){
        if(stock<= 0){
            throw new IllegalArgumentException("Price must be over 0.00");
        }
        setStock(stock);
    }

      public void changeCategory(String category){
        if(category.isBlank() || category.isEmpty()){
            throw new IllegalArgumentException(); 
        }
        setCategory(category);
    }

    public boolean checkStock(Long amount){
        boolean flag = (stock - amount)>0; 
        return flag;
    }

    public void decrementStock(Long amount){
        Long newTotal= stock - amount;
        changeStock(newTotal);
    }
}
