package com.store.api.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;


import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.store.api.Entity.Cart;
import com.store.api.Entity.Product;
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
        cart_serv.addItem(cart.getId(),1L, 3L);
        assertEquals(cart.getExistingItem(1L).get().getAmount(), 3L);
    }

    @Test
    void testEmptyCart() {   
        assertTrue(cart_repo.findById(cart.getId()).get().getItems_on_cart().isEmpty());
        cart_serv.addItem(cart.getId(),1L, 3L);
        assertFalse(cart_repo.findById(cart.getId()).get().getItems_on_cart().isEmpty());
        cart_serv.emptyCart(cart.getId());
        assertTrue(cart_repo.findById(cart.getId()).get().getItems_on_cart().isEmpty());
    }

    @Test
    void testGetCartNotFound() {
         when(cart_repo.findById(1L)).thenReturn(Optional.empty());
         assertThrows(EntityNotFoundException.class, ()->{cart_serv.getCart(1L);});
    }

    @Test
    void testListCartItems() {
        when(cart_repo.findById(cart.getId())).thenReturn(Optional.of(cart));
        cart_serv.addItem(cart.getId(), 2L,3L);
        cart_serv.addItem(cart.getId(), 4L,5L);
        assertEquals(cart_serv.listCartItems(cart.getId()).size(),2);
        //have to redefine hash and equality for cart items to check
    }

    @Test
    void testPrecalculateTotal() {
        setUpProducts();
        when(cart_repo.findById(cart.getId())).thenReturn(Optional.of(cart));
        when(prod_repo.findById(2L)).thenReturn(Optional.of(prod1));
        when(prod_repo.findById(4L)).thenReturn(Optional.of(prod2));
        cart_serv.addItem(cart.getId(), 2L,3L);
        cart_serv.addItem(cart.getId(), 4L,6L);
        assertEquals(cart_serv.precalculateTotal(cart.getId()), 105.00);
    }

    @Test
    void testRemoveItem() {
        cart_serv.addItem(cart.getId(),1L, 3L);
        cart_serv.removeItem(cart.getId(), 1L);
        assertTrue(cart_serv.listCartItems(cart.getId()).isEmpty());
    }

    @Test
    void testSetAmount() {
        cart_serv.addItem(cart.getId(),1L, 3L);
        cart_serv.setAmount(5L, 1L, 1L);
        assertEquals(cart.getItems_on_cart().stream().filter(i->i.getProduct_id().equals(1L)).findFirst().get().getAmount(),5L);
    }

    @Test
    void testSubstractItem() {
        cart_serv.addItem(cart.getId(),1L, 3L);
        cart_serv.substractItem(1L, 1L, 1L);
        assertEquals(cart.getExistingItem(1L).get().getAmount(), 2L);
    }

    private Cart cart;
    @BeforeEach
    private Cart createCart() {
        Cart cart = new Cart();
        cart.setId(1L);
        when(cart_repo.findById(cart.getId())).thenReturn(Optional.of(cart));
        return cart;
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

    private Product prod1;
    private Product prod2;

    @BeforeEach
    void setUpProducts() {
        cart = new Cart();
        cart.setId(1L);

        prod1 = generateProduct(2L, 3L, 15.00);
        prod2 = generateProduct(4L, 6L, 10.00);

        when(cart_repo.findById(cart.getId()))
            .thenReturn(Optional.of(cart));
    }
}
