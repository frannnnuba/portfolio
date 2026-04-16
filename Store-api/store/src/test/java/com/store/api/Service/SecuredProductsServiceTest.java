package com.store.api.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.store.api.DTOs.ProductDTO;
import com.store.api.Entity.Product;
import com.store.api.Mappers.MapperProduct;
import com.store.api.Repository.ProductsRepository;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class SecuredProductsServiceTest {

    @InjectMocks
    private SecuredProductsService prod_serv;

    @Mock
    private ProductsRepository prod_repo;

    @Test
    void testAddProductToCatalogue() {
        Product prod = createProduct();
        ProductDTO prodDTO = MapperProduct.toDto(prod);
        when(prod_repo.findById(1L)).thenReturn(Optional.empty());
        ProductDTO prodAdded = prod_serv.addProductToCatalogue(prodDTO);        
        assertEquals(prod, MapperProduct.toEntity(prodAdded));
    }

    @Test
    void testCantAddProductAlredyInCatalogue() {
        Product prod = createProduct();
        ProductDTO prodDTO = MapperProduct.toDto(prod);
        when(prod_repo.findById(1L)).thenReturn(Optional.of(prod));
        assertThrows(IllegalStateException.class,
            ()->{prod_serv.addProductToCatalogue(prodDTO);});  
    }

    @Test
    void testCheckAndDiscount() {
        Product prod = createProduct();
        when(prod_repo.findById(1L)).thenReturn(Optional.of(prod));
        prod_serv.checkAndDiscount(1L, 3L);
        assertEquals(0L,prod.getStock());;
    }

    @Test
    void testDoesntDiscountIfNotEnoughStock() {
        Product prod = createProduct();
        when(prod_repo.findById(1L)).thenReturn(Optional.of(prod));
        prod_serv.checkAndDiscount(1L, 4L);
        assertEquals(3L,prod.getStock());
    }

    @Test
    void testModifyProduct() {
        Product prod = createProduct();
        ProductDTO toChange = createProductDTO();
        assertNotEquals(prod.getProductName(), toChange.getProduct_name());
        assertNotEquals(prod.getBrand_name(), toChange.getBrand_name());
        assertNotEquals(prod.getPrice(), toChange.getPrice());
        assertNotEquals(prod.getStock(), toChange.getStock());
        assertNotEquals(prod.getCategory(), toChange.getCategory());
        when(prod_repo.findById(1L)).thenReturn(Optional.of(prod));
        prod_serv.modifyProduct(1L, toChange);
        assertEquals(prod.getProductName(), toChange.getProduct_name());
        assertEquals(prod.getBrand_name(), toChange.getBrand_name());
        assertEquals(prod.getPrice(), toChange.getPrice());
        assertEquals(prod.getStock(), toChange.getStock());
        assertEquals(prod.getCategory(), toChange.getCategory());
    }

    @Test
    void testPatchPrice() {
        Product prod = createProduct();
        Map<String,Object> updates = new HashMap<String,Object>();
        updates.put("price", 15.00);
        when(prod_repo.findById(1L)).thenReturn(Optional.of(prod));
        prod_serv.patchProduct(1L, updates);
        assertEquals(15, prod.getPrice());
    }

    @Test
    void testPatchStock() {
         Product prod = createProduct();
        Map<String,Object> updates = new HashMap<String,Object>();
        updates.put("stock", 10L);
        when(prod_repo.findById(1L)).thenReturn(Optional.of(prod));
        prod_serv.patchProduct(1L, updates);
        assertEquals(10, prod.getStock());
    }

    @Test
    void testRemoveProductFromCatalogue() {
        Product prod = createProduct();
        when(prod_repo.findById(1L)).thenReturn(Optional.of(prod));
        String msg =prod_serv.removeProductFromCatalogue(1L);
        verify(prod_repo).deleteById(1L);
        assertEquals("Product deleted successfully", msg);
    }

    @Test
    void testCantRemoveProductFromCatalogue() {
        when(prod_repo.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,()->{
            prod_serv.removeProductFromCatalogue(1L);});
    }

    public Product createProduct(){
        Product prod =new Product();
        prod.setId(1L);
        prod.setProductName("cheese");
        prod.setBrand_name("Milkaut");
        prod.setPrice(4.00);
        prod.setStock(3L);
        prod.setCategory("Dairy");
        return prod;
    }

    public ProductDTO createProductDTO(){
        Product prod =new Product();
        prod.setId(1L);
        prod.setProductName("meat");
        prod.setBrand_name("Wellington");
        prod.setPrice(7.00);
        prod.setStock(5L);
        prod.setCategory("Meats");
        return MapperProduct.toDto(prod);
    }
}
