package com.diegodev.hulkstore.data.entity.shop;

import com.diegodev.hulkstore.data.entity.ShopTransaction;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity(name = "purchases")
@Getter
@Setter
public class Purchase extends ShopTransaction {
    @ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @OneToOne
    @JoinColumn(name = "payment_detail_id")
    private Payment paymentDetail;
    @Column(name = "purchase_date")
    private Date purchaseDate;
}