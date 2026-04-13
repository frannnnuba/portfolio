package com.store.api.Service;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.api.DTOs.ProductDTO;
import com.store.api.Entity.Product;
import com.store.api.Mappers.MapperProduct;
import com.store.api.Repository.ProductsRepository;

@Service
public class SecuredProductsService {
    @Autowired
    private ProductsRepository prod_repo;

    public ProductDTO addProductToCatalogue(ProductDTO aNewProductDTO){
        if(prod_repo.findById(aNewProductDTO.getId()).isEmpty()){
            Product prod= MapperProduct.toEntity(aNewProductDTO);
            prod_repo.save(prod);
            return MapperProduct.toDto(prod);

        }else{
            throw new RuntimeException("Product already exists");
        }
    } 

    public String removeProductFromCatalogue(Long id){
        Optional<Product> prod = prod_repo.findById(id);
        if(prod.isPresent()){
            prod_repo.deleteById(id);
            return "Product deleted successfully";
        }else{
            throw new RuntimeException("Product doesn't exist");
        }
    } 

    public ProductDTO modifyProduct(Long id,ProductDTO aDTO){
        Optional<Product> prod = prod_repo.findById(id);
        if(prod.isPresent()){
           Product stored= prod.get();
           stored.setProduct_name(aDTO.getProduct_name());
           stored.setBrand_name(aDTO.getBrand_name());
           stored.setPrice(aDTO.getPrice());
           stored.setStock(aDTO.getStock());
           stored.setCategory(aDTO.getCategory());
           prod_repo.save(stored);
           return MapperProduct.toDto(stored);
            
        }else{
            throw new RuntimeException("Product doesn't exist");
        }
    }

    public ProductDTO patchProduct(Long id, Map<String,Object> updates){
        Optional<Product> prod = prod_repo.findById(id);
        if(prod.isPresent()){
            Product product = prod.get();
            updates.forEach((key,value)-> {switch (key) {
                case "product_name":
                    product.setProduct_name((String) value);
                    break;
                case "brand_name":
                    product.setBrand_name((String) value);
                    break;
                case "price":
                    product.setPrice(Double.valueOf(value.toString()));
                    break;     
                case "stock":
                    product.setStock(Long.valueOf(value.toString()));
                    break;
                case "category":
                    product.setCategory((String) value);
                    break;
                }  
            });
            prod_repo.save(product);
            return MapperProduct.toDto(product);
        }else{
            throw new RuntimeException("Product doesn't exist");
        }
    }
}
