package com.diegodev.hulkstore.data.service.repository;

import com.diegodev.hulkstore.data.entity.shop.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long>, JpaSpecificationExecutor<ShoppingCart> {

    @Query("SELECT sc FROM com.diegodev.hulkstore.data.entity.shop.ShoppingCart sc WHERE SIZE(sc.products) = 0 AND sc = :shoppingCart")
    List<ShoppingCart> isEmpty(@Param("shoppingCart") ShoppingCart shoppingCart);
}