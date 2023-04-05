package com.diegodev.hulkstore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity(name = "orderitems")
@Getter
@Setter
public class OrderItem extends AbstractEntity {
    @Column(name = "quantity")
    private @NotNull int quantity;
    @Column(name = "price")
    private @NotNull double price;
    @Column(name = "created_date")
    private Date createdDate;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;
    @OneToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;
}
