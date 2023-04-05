package com.diegodev.hulkstore.service;

import com.diegodev.hulkstore.SpringConfigurator;
import com.diegodev.hulkstore.model.OrderItem;
import com.diegodev.hulkstore.repository.OrderItemsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = {SpringConfigurator.class})
class OrderItemsServiceTest {

    @Mock
    private OrderItemsRepository repository;

    @InjectMocks
    private OrderItemsService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addOrderedProducts_ShouldSaveOrderItem() {
        // Given
        OrderItem orderItem = new OrderItem();

        // When
        service.addOrderedProducts(orderItem);

        // Then
        verify(repository, times(1)).save(orderItem);
    }
}