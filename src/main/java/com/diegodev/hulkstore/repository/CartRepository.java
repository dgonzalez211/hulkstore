package com.diegodev.hulkstore.repository;

import com.diegodev.hulkstore.model.Cart;
import com.diegodev.hulkstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    List<Cart> findAllByUserOrderByCreatedDateDesc(User user);

    void deleteByUser(User user);

    Cart findByUser(User user);

}

