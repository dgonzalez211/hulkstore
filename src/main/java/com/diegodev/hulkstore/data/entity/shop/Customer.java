package com.diegodev.hulkstore.data.entity.shop;

import com.diegodev.hulkstore.data.entity.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity(name = "customers")
@Getter
@Setter
public class Customer extends User {

    @OneToOne
    @JoinColumn(name = "shopping_cart_id")
    private ShoppingCart shoppingCart;
    @OneToMany(mappedBy = "customer")
    private List<Purchase> purchases;
    @OneToMany(mappedBy = "customer")
    private List<Payment> payments;

}