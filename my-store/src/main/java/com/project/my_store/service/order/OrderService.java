package com.project.my_store.service.order;

import com.project.my_store.dto.OrderDto;
import com.project.my_store.enums.OrderStatus;
import com.project.my_store.exceptions.ResourceNotFoundException;
import com.project.my_store.model.*;
import com.project.my_store.repository.OrderRepository;
import com.project.my_store.repository.ProductRepository;
import com.project.my_store.service.cart.CartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService{

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartService cartService;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public Order placeOrder(Long userId) {
        Cart cart   = cartService.getCartByUserId(userId);

        Order order = createOrder(cart);
        List<OrderItem> orderItemList = createOrderItems(order, cart);

        order.setOrderItems(new HashSet<>(orderItemList));
        order.setTotalAmount(calculateTotalAmount(orderItemList));
        Order savedOrder = orderRepository.save(order);

        cartService.clearCart(cart.getId());

        return savedOrder;
        }

    private Order createOrder(Cart cart) {
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());
        return  order;
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
    public OrderDto getOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        return convertToDto(order);
    }

    @Override
    public List<OrderDto> getUserOrders(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return  orders.stream()
                .map(this :: convertToDto)
                .toList();
    }


    private OrderDto convertToDto(Order order) {
        return modelMapper.map(order, OrderDto.class);
    }



}
