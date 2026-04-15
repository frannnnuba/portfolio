package com.store.api.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.store.api.DTOs.OrderDTO;
import com.store.api.DTOs.OrderItemDTO;
import com.store.api.Entity.Order;
import com.store.api.Entity.OrderItem;
import com.store.api.Entity.Product;
import com.store.api.Mappers.MapperOrder;
import com.store.api.Mappers.MapperOrderITem;
import com.store.api.Repository.OrderRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class OrderService {

    private final OrderRepository order_repo;
    private final SecuredProductsService prod_serv;
    
    public OrderService(OrderRepository order_repo,SecuredProductsService prod_serv) {
        this.order_repo = order_repo;
        this.prod_serv=prod_serv;
    }

    public boolean isApproved(Long id) {
        Order order= order_repo.findById(id).orElseThrow();
        return order.isApproved(); 
    }

    public boolean isPending(Long id) {
        Order order= order_repo.findById(id).orElseThrow();
        return order.isPending(); 
    }

    public Double getBalance(Long userId){
        List<Order> orders = order_repo.findByUserId(userId);
        return orders.stream()
               .filter(Order::isApproved)
               .map(Order::getTotal)
               .reduce(0.0, Double::sum);
    }

    @Transactional
    public Set<OrderItemDTO> listItems(Long orderId) {
        return order_repo.findById(orderId).orElseThrow().getOrder_items().stream().
        map(MapperOrderITem::toDto).collect(Collectors.toSet());
    }

    public Set<OrderDTO> listOrders(Long userId) {
        return order_repo.findByUserId(userId).stream().
        map(MapperOrder::toDto).collect(Collectors.toSet());
    }

    public OrderDTO getOrder(Long orderId) {
        return MapperOrder.toDto(order_repo.findById(orderId).orElseThrow());
    }

    public void markPaid(Long orderId){
        Order order= order_repo.findById(orderId).orElseThrow();
        order.markPaid();
    }

    public void markPaymentFailed(Long orderId){
         Order order= order_repo.findById(orderId).orElseThrow();
        order.markPaymentFailed();
    }

    @Transactional
    public void discountStockForOrder(Long orderID){
        Order order = order_repo.findById(orderID).orElseThrow(()-> new EntityNotFoundException());
        for(OrderItem oi: order.getOrder_items()){
            Long prodId = oi.getProduct_id();
            Long amount = oi.getQuantity();
            prod_serv.checkAndDiscount(prodId,amount);
        }
        markPaid(orderID);
    }
}
