package com.diegodev.hulkstore.service;

import com.diegodev.hulkstore.model.OrderItem;
import com.diegodev.hulkstore.repository.OrderItemsRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class OrderItemsService extends CrudBaseService<OrderItemsRepository, OrderItem>{
    private final OrderItemsRepository repository;

    public OrderItemsService(OrderItemsRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public void addOrderedProducts(OrderItem orderItem) {
        save(orderItem);
    }


}
