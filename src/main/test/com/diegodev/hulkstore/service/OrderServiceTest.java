package com.diegodev.hulkstore.service;

import com.diegodev.hulkstore.SpringConfigurator;
import com.diegodev.hulkstore.exceptions.OrderNotFoundException;
import com.diegodev.hulkstore.model.Order;
import com.diegodev.hulkstore.model.User;
import com.diegodev.hulkstore.repository.CartRepository;
import com.diegodev.hulkstore.repository.OrderItemsRepository;
import com.diegodev.hulkstore.repository.OrderRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

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