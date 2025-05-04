package com.project.my_store.service.cart;

import com.project.my_store.exceptions.ResourceNotFoundException;
import com.project.my_store.model.Cart;
import com.project.my_store.repository.CartItemRepository;
import com.project.my_store.repository.CartRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService{

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public Cart getCart(Long id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Cart Not Fount"));
        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        return cartRepository.save(cart);
    }

    @Transactional                          //ie Either all steps complete or none
    @Override
    public void clearCart(Long id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Cart Not Fount"));
        cartItemRepository.deleteAllByCartId(id);
        cartRepository.delete(cart);
    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        Cart cart = getCart(id);
        return cart.getTotalAmount();
    }

    @Override
    public Long initializeNewCart() {
        Cart newCart = new Cart();
        Cart savedCart = cartRepository.save(newCart);
        return savedCart.getId(); // the database-generated unique ID
    }

//    @Override
//    public Cart getCartByUserId(Long userId) {
//        return cartRepository.findByUserId(userId);
//    }
}
