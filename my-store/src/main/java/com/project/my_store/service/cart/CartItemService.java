package com.project.my_store.service.cart;

import com.project.my_store.exceptions.ResourceNotFoundException;
import com.project.my_store.model.Cart;
import com.project.my_store.model.CartItem;
import com.project.my_store.model.Product;
import com.project.my_store.repository.CartItemRepository;
import com.project.my_store.repository.CartRepository;
import com.project.my_store.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService{

    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final IProductService productService;
    private final ICartService cartService;

    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {
        Cart cart = cartService.getCart(cartId);
        Product product = productService.getProductById(productId);

        //existing item
        CartItem item = null;
        for (CartItem i : cart.getItems()) {
            if (i.getProduct().getId() == productId.longValue()) {
                item = i;
                break;
            }
        }
        if (item != null) {
            item.setQuantity(item.getQuantity() + quantity);
            item.setTotalPrice();
        }
        else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            newItem.setUnitPrice(product.getPrice());
            newItem.setTotalPrice();
            cart.addItem(newItem);
        }

        cartRepository.save(cart);
    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
        CartItem itemToRemove = getCartItem(cartId, productId);
        cart.removeItem(itemToRemove);
        cartRepository.save(cart);
    }

    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity) {
        Cart cart = cartService.getCart(cartId);

        //find and update the item
        for (CartItem i : cart.getItems()) {
            if (i.getProduct().getId().equals(productId)) {
                i.setQuantity(quantity);
                i.setUnitPrice(i.getProduct().getPrice());
                i.setTotalPrice();
                break;  //once we find and update the item
            }
        }
        //new total amount
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (CartItem j : cart.getItems()) {
            totalAmount = totalAmount.add(j.getTotalPrice());
        }
        //update and save cart
        cart.setTotalAmount(totalAmount);
        cartRepository.save(cart);
    }

    @Override
    public CartItem getCartItem(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);

        for (CartItem item : cart.getItems()) {
            if (item.getProduct().getId().equals(productId)) {
                return item;
            }
        }
        throw new ResourceNotFoundException("Item not found");
    }
}
