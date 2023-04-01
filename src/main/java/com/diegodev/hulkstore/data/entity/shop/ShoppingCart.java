package com.diegodev.hulkstore.data.entity.shop;

import com.diegodev.hulkstore.data.entity.AbstractEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity(name = "shopping_carts")
@Getter
@Setter
public class ShoppingCart extends AbstractEntity {
    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @OneToMany
    private List<Product> products;
}
