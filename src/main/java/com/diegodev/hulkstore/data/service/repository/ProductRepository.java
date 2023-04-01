package com.diegodev.hulkstore.data.service.repository;

import com.diegodev.hulkstore.data.entity.shop.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    @Query("select p from com.diegodev.hulkstore.data.entity.shop.Product p " +
            "where lower(p.code) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(p.name) like lower(concat('%', :searchTerm, '%'))")
    List<Product> search(@Param("searchTerm") String searchTerm);
}