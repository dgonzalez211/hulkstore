package com.diegodev.hulkstore.model;

import com.diegodev.hulkstore.dto.product.ProductDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity(name = "products")
@Getter
@Setter
@NoArgsConstructor
public class Product extends AbstractEntity {
    private @NotNull String code;
    private @NotNull String name;
    private @NotNull String imageURL;
    private @NotNull Double price;
    private @NotNull Double stock;
    private @NotNull String description;
    private @NotNull String category;
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    private List<Cart> carts;


    public Product(ProductDto productDto, String category) {
        this.name = productDto.getName();
        this.code = productDto.getCode();
        this.imageURL = productDto.getImageURL();
        this.description = productDto.getDescription();
        this.price = productDto.getPrice();
        this.category = category;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                '}';
    }
}
