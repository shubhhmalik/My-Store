package com.project.my_store.service.order;

import com.project.my_store.dto.OrderDto;
import com.project.my_store.model.Order;

import java.util.List;

public interface IOrderService {
    Order placeOrder(Long userId);
    OrderDto getOrder(Long orderId);
    List<OrderDto> getUserOrders(Long userId);
}
