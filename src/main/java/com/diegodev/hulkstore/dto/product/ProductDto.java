package com.diegodev.hulkstore.dto.product;

import com.diegodev.hulkstore.model.Product;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDto {

    private Integer id;
    private @NotNull String code;
    private @NotNull String name;
    private @NotNull String imageURL;
    private @NotNull double price;
    private @NotNull double stock;
    private @NotNull String description;
    private @NotNull String category;

    public ProductDto(Product product) {
        this.setId(product.getId());
        this.setCode(product.getCode());
        this.setName(product.getName());
        this.setImageURL(product.getImageURL());
        this.setDescription(product.getDescription());
        this.setPrice(product.getPrice());
        this.setStock(product.getStock());
        this.setCategory(product.getCategory());
    }
}