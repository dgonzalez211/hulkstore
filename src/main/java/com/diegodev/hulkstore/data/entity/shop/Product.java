package com.diegodev.hulkstore.data.entity.shop;

import com.diegodev.hulkstore.data.entity.Item;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "products")
@Getter
@Setter
public class Product extends Item {

    @Column(name = "quantity")
    private Double quantity;
    @Column(name = "product_image_url")
    private String productImageUrl;
    @Column(name = "stock")
    private Double stock;
    @Column(name = "price")
    private Double price;
    @Column(name = "out_of_stock")
    private boolean outOfStock;
}
