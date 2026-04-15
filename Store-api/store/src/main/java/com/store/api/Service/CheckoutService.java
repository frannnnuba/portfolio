package com.store.api.Service;

import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.store.api.Entity.Cart;
import com.store.api.Entity.CartItem;
import com.store.api.Entity.Order;
import com.store.api.Entity.OrderItem;
import com.store.api.Entity.Product;
import com.store.api.Entity.StateOfOrder;
import com.store.api.Repository.CartRepository;
import com.store.api.Repository.OrderRepository;
import com.store.api.Repository.ProductsRepository;

@Service
public class CheckoutService {
    private final OrderRepository order_repo;
    private final CartRepository cart_repo;
    private final ProductsRepository prod_repo;

    public CheckoutService(OrderRepository order_repo, CartRepository cart_repo,ProductsRepository prod_repo) {
        this.order_repo = order_repo;
        this.cart_repo = cart_repo;
        this.prod_repo = prod_repo;
    }

    @Transactional
    public Long checkout(Long cartId){
        Cart cart = cart_repo.findById(cartId).orElseThrow();

        Set<CartItem> cartItems = cart.getItems_on_cart();
        assertItemsNoEmpty(cartItems);

        Order newOrder = new Order();
        cartItems.forEach(i->mapCartItemToOrderItem(newOrder, i));
        newOrder.setUser(cart.getUser());
        newOrder.setTotal(cart.precalculateTotal());
        newOrder.setState(StateOfOrder.PENDING);
        order_repo.save(newOrder);
        cart_repo.delete(cart);

        return newOrder.getId();
    }

    private void mapCartItemToOrderItem(Order newOrder, CartItem ci) {
        OrderItem orderItem= new OrderItem();
        Product product = prod_repo.findById(ci.getProduct_id()).get();

        orderItem.setProduct_id(ci.getProduct_id());
        orderItem.setProduct_name(product.getProductName());
        orderItem.setPrice(ci.getPrice());
        orderItem.setQuantity(ci.getAmount());

        newOrder.addItem(orderItem);
    }

    private void assertItemsNoEmpty(Set<CartItem> cartItems) {
        if(cartItems.isEmpty()){
            throw new RuntimeException("Can't checkout empty cart");
        }
    }
    
}
