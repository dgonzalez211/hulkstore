package com.diegodev.hulkstore.repository;

import com.diegodev.hulkstore.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemsRepository extends JpaRepository<OrderItem,Integer> {
}
