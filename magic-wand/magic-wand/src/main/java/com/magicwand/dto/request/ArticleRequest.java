package com.magicwand.dto.request;

import com.magicwand.enums.ArticleType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ArticleRequest {

    @NotBlank(message = "El nombre del artículo es obligatorio")
    private String name;

    @NotNull(message = "El tipo del artículo es obligatorio")
    private ArticleType type;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
    private BigDecimal price;

    @NotNull(message = "El ID del proveedor es obligatorio")
    private Long providerId;
}
