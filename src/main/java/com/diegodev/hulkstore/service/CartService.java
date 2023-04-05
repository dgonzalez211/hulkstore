package com.diegodev.hulkstore.service;

import com.diegodev.hulkstore.dto.cart.AddToCartDto;
import com.diegodev.hulkstore.dto.cart.CartDto;
import com.diegodev.hulkstore.dto.cart.CartItemDto;
import com.diegodev.hulkstore.exceptions.CartItemNotExistException;
import com.diegodev.hulkstore.model.Cart;
import com.diegodev.hulkstore.model.Product;
import com.diegodev.hulkstore.model.User;
import com.diegodev.hulkstore.repository.CartRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class CartService extends CrudBaseService<CartRepository, Cart> {

    private final CartRepository repository;

    public CartService(CartRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public void addToCart(int quantity, Product product, User user){
        Cart cart = new Cart(product, quantity, user);
        save(cart);
    }

    public List<Cart> findAll() {
        return repository.findAll();
    }

    public CartDto listCartItems(User user) {
        List<Cart> cartList = repository.findAllByUserOrderByCreatedDateDesc(user);
        List<CartItemDto> cartItems = new ArrayList<>();

        cartList.forEach(cart -> {
            CartItemDto cartItemDto = getDtoFromCart(cart);
            cartItems.add(cartItemDto);
        });

        double totalCost = 0;
        for (CartItemDto cartItemDto :cartItems){
            totalCost += (cartItemDto.getProduct().getPrice()* cartItemDto.getQuantity());
        }
        return new CartDto(cartItems,totalCost);
    }

    public static CartItemDto getDtoFromCart(Cart cart) {
        return new CartItemDto(cart);
    }


    public void updateCartItem(AddToCartDto cartDto){
        Cart cart = repository.getOne(cartDto.getId());
        cart.setQuantity(cartDto.getQuantity());
        cart.setCreatedDate(new Date());
        save(cart);
    }

    public Cart findCartByUser(User user) {
        return repository.findByUser(user);
    }

    public void deleteCartItem(int id,int userId) throws CartItemNotExistException {
        if (!repository.existsById(id))
            throw new CartItemNotExistException("Cart id is invalid : " + id);
        delete(id);

    }

    public void deleteCartItems(int userId) {
        repository.deleteAll();
    }

    public void deleteUserCartItems(User user) {
        repository.deleteByUser(user);
    }
}


