package com.store.api.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.store.api.DTOs.CartDTO;
import com.store.api.Entity.Cart;
import com.store.api.Entity.CartItem;
import com.store.api.Entity.Product;
import com.store.api.Mappers.MapperCart;
import com.store.api.Repository.CartRepository;
import com.store.api.Repository.ProductsRepository;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @InjectMocks
    private CartService cart_serv;

    @Mock
    private CartRepository cart_repo;

    @Mock
    private ProductsRepository prod_repo;

    @Test
    void testAddItem() {
        Cart cart = new Cart();
        cart.setId(1L);
        when(cart_repo.findById(cart.getId())).thenReturn(Optional.of(cart));
        cart_serv.addItem(cart.getId(),1L, 3L);
        assertEquals(cart.getExistingItem(1L).get().getAmount(), 3L);
    }

    @Test
    void testEmptyCart() {   
        CartDTO cartDTO= cart_serv.createCart();
        assertTrue(cartDTO.getItems_on_cart().isEmpty());
    }
    

    @Test
    void testGetCartNotFound() {
         when(cart_repo.findById(1L)).thenReturn(Optional.empty());
         assertThrows(EntityNotFoundException.class, ()->{cart_serv.getCart(1L);});
    }

    @Test
    void testListCartItems() {
        CartDTO cartDTO= cart_serv.createCart();
        Cart cart= MapperCart.toEntity(cartDTO);
        when(cart_repo.findById(cart.getId())).thenReturn(Optional.of(cart));
        cart_serv.addItem(cart.getId(), 2L,3L);
        cart_serv.addItem(cart.getId(), 4L,5L);
        assertEquals(cart_serv.listCartItems(cart.getId()).size(),2);
        //have to redefine hash and equality for cart items to check
    }


    @Test
    void testPrecalculateTotal() {
        CartDTO cartDTO= cart_serv.createCart();
        Cart cart= MapperCart.toEntity(cartDTO);
        Product prod1 = generateProduct(2L, 3L, 15.00);
        Product prod2 = generateProduct(4L, 6L, 10.00);
        when(cart_repo.findById(cart.getId())).thenReturn(Optional.of(cart));
        when(prod_repo.findById(2L)).thenReturn(Optional.of(prod1));
        when(prod_repo.findById(4L)).thenReturn(Optional.of(prod2));
        cart_serv.addItem(cart.getId(), 2L,3L);
        cart_serv.addItem(cart.getId(), 4L,6L);
        assertEquals(cart_serv.precalculateTotal(cart.getId()), 105.00);
    }

    @Test
    void testRemoveItem() {
        Cart cart = new Cart();
        cart.setId(1L);
        when(cart_repo.findById(cart.getId())).thenReturn(Optional.of(cart));
        cart_serv.addItem(cart.getId(),1L, 3L);
        cart_serv.removeItem(cart.getId(), 1L);
        assertTrue(cart_serv.listCartItems(cart.getId()).isEmpty());
    }

    @Test
    void testSetAmount() {
        Cart cart = new Cart();
        cart.setId(1L);
        when(cart_repo.findById(cart.getId())).thenReturn(Optional.of(cart));
        cart_serv.addItem(cart.getId(),1L, 3L);
        cart_serv.setAmount(5L, 1L, 1L);
        assertEquals(cart.getItems_on_cart().stream().filter(i->i.getProduct_id().equals(1L)).findFirst().get().getAmount(),5L);
    }

    @Test
    void testSubstractItem() {
        Cart cart = new Cart();
        cart.setId(1L);
        when(cart_repo.findById(cart.getId())).thenReturn(Optional.of(cart));
        cart_serv.addItem(cart.getId(),1L, 3L);
        cart_serv.substractItem(1L, 1L, 1L);
        assertEquals(cart.getExistingItem(1L).get().getAmount(), 2L);
    }

    public Product generateProduct(Long id,Long amount,Double price){
        Product prod =new Product();
        prod.setId(id);
        prod.setProductName("cheese");
        prod.setBrand_name("Milkaut");
        prod.setPrice(price);
        prod.setStock(amount);
        prod.setCategory("Dairy");
        return prod;
    }

}
