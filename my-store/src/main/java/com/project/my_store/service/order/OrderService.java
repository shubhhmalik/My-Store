package com.project.my_store.service.order;

import com.project.my_store.exceptions.ResourceNotFoundException;
import com.project.my_store.model.*;
import com.project.my_store.repository.OrderRepository;
import com.project.my_store.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService{

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Override
    public Order placeOrder(Long userId) {
        return null;
    }

    private BigDecimal calculateTotalAmount(List<OrderItem> orderItemList) {
        BigDecimal total = BigDecimal.ZERO;

        for (OrderItem item : orderItemList) {
            BigDecimal itemTotal = item.getPrice().multiply(new BigDecimal(item.getQuantity()));
            total = total.add(itemTotal);
        }

        return total;
    }


    private List<OrderItem> createOrderItems(Order order, Cart cart) {
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem i : cart.getItems()) {
            Product product = i.getProduct();
            //update product inventory
            product.setInventory(product.getInventory() - i.getQuantity());
            productRepository.save(product);

            //create new order item
            OrderItem orderItem = new OrderItem(
                    order,
                    product,
                    i.getQuantity(),
                    i.getUnitPrice()
            );
            orderItems.add(orderItem);
        }

        return orderItems;
    }

    @Override
    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(()->new ResourceNotFoundException("Order Not Found!"));
    }
}
