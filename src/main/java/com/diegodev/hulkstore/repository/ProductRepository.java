package com.diegodev.hulkstore.repository;

import com.diegodev.hulkstore.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("select p from com.diegodev.hulkstore.model.Product p " +
            "where lower(p.code) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(p.name) like lower(concat('%', :searchTerm, '%'))")
    List<Product> search(@Param("searchTerm") String searchTerm);

    Product findProductByCode(String code);

    @Query("SELECT p FROM com.diegodev.hulkstore.model.Product p WHERE p.stock = 0 AND p.id = :productId")
    Product findProductWithStockEqualZero(Integer productId);
}
