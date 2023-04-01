package com.diegodev.hulkstore.data.entity.shop;

import com.diegodev.hulkstore.data.constants.PaymentMethods;
import com.diegodev.hulkstore.data.entity.ShopTransaction;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "payments")
@Getter
@Setter
public class Payment extends ShopTransaction {

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private PaymentMethods paymentMethod;
}