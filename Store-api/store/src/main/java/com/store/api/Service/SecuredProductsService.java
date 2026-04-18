package com.store.api.Service;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.store.api.DTOs.ProductDTO;
import com.store.api.Entity.Product;
import com.store.api.Mappers.MapperProduct;
import com.store.api.Repository.ProductsRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class SecuredProductsService {
    @Autowired
    private ProductsRepository prod_repo;

    @Transactional
    public ProductDTO addProductToCatalogue(ProductDTO aNewProductDTO){
        Product prod= MapperProduct.toEntity(aNewProductDTO);
        prod_repo.save(prod);
        return MapperProduct.toDto(prod);
    } 

    public String removeProductFromCatalogue(Long id){
        prod_repo.findById(id).orElseThrow(
            ()-> new EntityNotFoundException("Can't delete not present product"));
        prod_repo.deleteById(id);
        return "Product deleted successfully";
    } 

    @Transactional
    public ProductDTO modifyProduct(Long id,ProductDTO aDTO){
        Optional<Product> prod = prod_repo.findById(id);
        if(prod.isPresent()){
           Product stored= prod.get();
           stored.modifyProduct(aDTO);
           return MapperProduct.toDto(stored);
        }else{
            throw new EntityNotFoundException("Product doesn't exist");
        }
    }

    @Transactional
    public ProductDTO patchProduct(Long id, Map<String,Object> updates){
        Optional<Product> prod = prod_repo.findById(id);
        if(prod.isPresent()){
            Product product = prod.get();
            updates.forEach((key,value)-> {switch (key) {
                case "product_name":
                    product.changeProductName((String) value);
                    break;
                case "brand_name":
                    product.changeBrandName((String) value);
                    break;
                case "price":
                    product.changePrice(Double.valueOf(value.toString()));
                    break;     
                case "stock":
                    product.changeStock(Long.valueOf(value.toString()));
                    break;
                case "category":
                    product.changeCategory((String) value);
                    break;
                }  
            });
            //prod_repo.save(product);
            return MapperProduct.toDto(product);
        }else{
            throw new EntityNotFoundException("Product doesn't exist");
        }
    }

    public void checkAndDiscount(Long prodId,Long amount){
        Product prod = prod_repo.findById(prodId).orElseThrow(()->new EntityNotFoundException());
        if(prod.checkStock(amount)){
            prod.decrementStock(amount);
        }
    }
}
