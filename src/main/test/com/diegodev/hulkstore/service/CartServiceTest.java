package com.diegodev.hulkstore.service;

import com.diegodev.hulkstore.SpringConfigurator;
import com.diegodev.hulkstore.dto.cart.AddToCartDto;
import com.diegodev.hulkstore.exceptions.CartItemNotExistException;
import com.diegodev.hulkstore.model.Cart;
import com.diegodev.hulkstore.model.Product;
import com.diegodev.hulkstore.model.User;
import com.diegodev.hulkstore.repository.CartRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = { SpringConfigurator.class })
public class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private CartService cartService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void addToCart_Should_AddItemToCart() {
        // Arrange
        int quantity = 1;
        User user = new User();
        Product product = new Product();
        product.setPrice(10.0);
        Cart expectedCart = new Cart(product, quantity, user);

        when(cartRepository.save(any(Cart.class)))
                .thenReturn(expectedCart);

        // Act
        cartService.addToCart(quantity, product, user);
        Cart actualCart = cartRepository.save(expectedCart);

        // Assert
        Assertions.assertEquals(expectedCart, actualCart);
    }

    @Test
    public void testUpdateCartItem() {
        // Arrange
        User user = new User();
        Product product = new Product();
        product.setId(1);
        int quantity = 2;

        Cart cart = new Cart(product, quantity, user);
        cart.setId(1);

        AddToCartDto cartDto = new AddToCartDto();
        cartDto.setId(cart.getId());
        cartDto.setQuantity(4);

        when(cartRepository.getOne(cart.getId())).thenReturn(cart);
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        // Act
        cartService.updateCartItem(cartDto);

        // Assert
        verify(cartRepository, times(1)).getOne(cart.getId());
        verify(cartRepository, times(1)).save(cart);
        Assertions.assertEquals(4, cart.getQuantity());
    }

    @Test
    public void testDeleteUserCartItems() throws CartItemNotExistException {
        // Arrange
        User user = new User();
        int quantity = 2;
        Product product = new Product();
        product.setId(1);

        Cart cart = new Cart(product, quantity, user);
        cart.setQuantity(quantity);

        // Act
        cartService.deleteUserCartItems(user);

        // Assert
        verify(cartRepository, times(1)).deleteByUser(user);
    }
}
