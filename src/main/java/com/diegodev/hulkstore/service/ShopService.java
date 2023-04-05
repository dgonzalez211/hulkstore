package com.diegodev.hulkstore.service;

import com.diegodev.hulkstore.dto.cart.CartDto;
import com.diegodev.hulkstore.exceptions.ProductNotExistException;
import com.diegodev.hulkstore.model.Product;
import com.diegodev.hulkstore.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ShopService {

    private CartService cartService;
    private ProductService productService;
    private OrderService orderService;

    public void addToCart(int quantity, Product product, User user) {
        cartService.addToCart(quantity, product, user);
    }

    public List<Product> findAllProducts(String stringFilter) {
        return productService.findAllProducts(stringFilter);
    }

    public boolean isOutOfStock(Integer id) {
        return productService.isOutOfStock(id);
    }

    public Product getProductById(Integer productId) throws ProductNotExistException {
        return productService.getProductById(productId);
    }

    public CartDto listCartItems(User user) {
        return cartService.listCartItems(user);
    }

    public void emptyUserCart(User user) {
        cartService.deleteUserCartItems(user);
    }

    public boolean isUserCartEmpty(User user) {
        return cartService.listCartItems(user).getCartItems().isEmpty();
    }

    public void placeOrder(User user) {
        orderService.placeOrder(user);
    }

}
