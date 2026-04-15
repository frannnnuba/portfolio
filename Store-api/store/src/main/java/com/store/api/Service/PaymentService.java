package com.store.api.Service;

import org.springframework.stereotype.Service;

import com.store.api.DTOs.OrderDTO;

@Service
public class PaymentService {

    private final OrderService order_serv;
    

    public PaymentService(OrderService order_serv) {
        this.order_serv = order_serv;
    }

    public void payOrder(Long orderId){
        if(!order_serv.isPending(orderId)){
            throw new IllegalArgumentException("Order id is not pending payment");
        }
        OrderDTO orderDto= order_serv.getOrder(orderId);
        if(charge(orderDto.getTotal())){
            order_serv.discountStockForOrder(orderId);
        }else{
            order_serv.markPaymentFailed(orderId);
        }
    }


    //just simulate payment for now
    public boolean charge(Double amount){
        return 10.00<amount;
    }

}
