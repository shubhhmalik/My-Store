package com.project.my_store.service.order;

import com.project.my_store.model.Order;

public interface IOrderService {
    Order placeOrder(Long userId);
    Order getOrder(Long orderId);

    Order getUserOrder(Long userId);
}
