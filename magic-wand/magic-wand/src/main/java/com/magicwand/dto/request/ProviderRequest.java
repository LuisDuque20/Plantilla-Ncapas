package com.magicwand.dto.request;

import com.magicwand.enums.ArticleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProviderRequest {

    @NotBlank(message = "El nombre del proveedor es obligatorio")
    private String name;

    @NotNull(message = "El tipo del proveedor es obligatorio")
    private ArticleType type;
}
