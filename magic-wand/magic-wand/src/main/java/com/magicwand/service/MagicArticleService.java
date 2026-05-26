package com.magicwand.service;

import com.magicwand.dto.request.ArticleRequest;
import com.magicwand.dto.response.ArticleResponse;
import com.magicwand.enums.ArticleType;

import java.math.BigDecimal;
import java.util.List;

public interface MagicArticleService {

    ArticleResponse create(ArticleRequest request);

    List<ArticleResponse> findAll(ArticleType category, BigDecimal maxPrice, Long providerId);

    ArticleResponse findById(Long id);

    ArticleResponse update(Long id, ArticleRequest request);

    void delete(Long id);
}
