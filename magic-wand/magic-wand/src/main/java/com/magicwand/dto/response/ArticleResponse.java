package com.magicwand.dto.response;

import com.magicwand.enums.ArticleType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ArticleResponse {
    private Long id;
    private String name;
    private ArticleType type;
    private BigDecimal price;
    private ProviderResponse provider;
}
