package com.diegodev.hulkstore.service;

import com.diegodev.hulkstore.SpringConfigurator;
import com.diegodev.hulkstore.dto.cart.CartDto;
import com.diegodev.hulkstore.dto.cart.CartItemDto;
import com.diegodev.hulkstore.exceptions.OrderNotFoundException;
import com.diegodev.hulkstore.model.Order;
import com.diegodev.hulkstore.model.OrderItem;
import com.diegodev.hulkstore.model.Product;
import com.diegodev.hulkstore.model.User;
import com.diegodev.hulkstore.repository.CartRepository;
import com.diegodev.hulkstore.repository.OrderItemsRepository;
import com.diegodev.hulkstore.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = {SpringConfigurator.class})
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private CartService cartService;

    @InjectMocks
    private ProductService productService;

    @Mock
    private OrderItemsRepository orderItemsRepository;

    @InjectMocks
    private OrderService orderService;

    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1);
        user.setUsername("testuser");
    }

    @Test
    public void placeOrder_shouldPlaceOrder() {
        // Arrange

        List<CartItemDto> cartItemDtoList = new ArrayList<>();
        Product product = new Product();
        product.setId(1);
        product.setName("testproduct");
        product.setPrice(100.0);
        product.setStock(10.0);
        cartItemDtoList.add(new CartItemDto());

        CartDto cartDto = new CartDto(cartItemDtoList, 200.0);
        when(cartService.listCartItems(user)).thenReturn(cartDto);

        ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
        ArgumentCaptor<OrderItem> orderItemCaptor = ArgumentCaptor.forClass(OrderItem.class);

        // Act
        orderService.placeOrder(user);

        // Assert
        verify(orderRepository).save(orderCaptor.capture());
        verify(orderItemsRepository, times(2)).save(orderItemCaptor.capture());
        verify(cartService).deleteUserCartItems(user);
        verify(productService, times(2)).updateProductStock(any(Product.class), anyInt());

        Order capturedOrder = orderCaptor.getValue();
        assertEquals(user, capturedOrder.getUser());
        assertEquals(cartDto.getTotalCost(), capturedOrder.getTotalPrice());

        List<OrderItem> capturedOrderItems = orderItemCaptor.getAllValues();
        assertEquals(cartItemDtoList.size(), capturedOrderItems.size());
        for (int i = 0; i < cartItemDtoList.size(); i++) {
            assertEquals(cartItemDtoList.get(i).getQuantity(), capturedOrderItems.get(i).getQuantity());
            assertEquals(cartItemDtoList.get(i).getProduct(), capturedOrderItems.get(i).getProduct());
            assertEquals(cartItemDtoList.get(i).getProduct().getPrice(), capturedOrderItems.get(i).getPrice());
            assertEquals(capturedOrder, capturedOrderItems.get(i).getOrder());
        }
    }

    @Test
    public void listOrders_shouldReturnListOfOrdersForUser() {
        // Arrange
        List<Order> orderList = new ArrayList<>();
        Order order1 = new Order();
        Order order2 = new Order();
        orderList.add(order1);
        orderList.add(order2);
        when(orderRepository.findAllByUserOrderByCreatedDateDesc(user)).thenReturn(orderList);

        // Act
        List<Order> resultList = orderService.listOrders(user);

        // Assert
        assertEquals(orderList, resultList);
    }

    @Test
    public void getOrder_shouldReturnOrderIfExists() throws OrderNotFoundException {
        // Arrange
        Order order = new Order();
        order.setId(1);
        Optional<Order> optionalOrder = Optional.of(order);
        when(orderRepository.findById(1)).thenReturn(optionalOrder);

        // Act
        Order resultOrder = orderService.getOrder(1);

        // Assert
        assertEquals(order, resultOrder);
    }
}