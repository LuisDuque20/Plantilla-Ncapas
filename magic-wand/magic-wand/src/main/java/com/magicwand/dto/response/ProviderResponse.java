package com.magicwand.dto.response;

import com.magicwand.enums.ArticleType;
import lombok.Data;

@Data
public class ProviderResponse {
    private Long id;
    private String name;
    private ArticleType type;
}
