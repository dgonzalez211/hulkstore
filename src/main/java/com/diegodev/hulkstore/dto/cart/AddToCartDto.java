package com.diegodev.hulkstore.dto.cart;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddToCartDto {
    private Integer id;
    private @NotNull Integer productId;
    private @NotNull Integer quantity;

    @Override
    public String toString() {
        return "CartDto{" +
                "id=" + id +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ",";
    }
}