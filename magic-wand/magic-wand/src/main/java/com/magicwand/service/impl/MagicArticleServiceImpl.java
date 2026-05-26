package com.magicwand.service.impl;

import com.magicwand.dto.request.ArticleRequest;
import com.magicwand.dto.response.ArticleResponse;
import com.magicwand.entity.MagicArticle;
import com.magicwand.entity.MagicProvider;
import com.magicwand.enums.ArticleType;
import com.magicwand.exception.BusinessRuleException;
import com.magicwand.exception.ConflictException;
import com.magicwand.exception.ResourceNotFoundException;
import com.magicwand.mapper.ArticleMapper;
import com.magicwand.repository.MagicArticleRepository;
import com.magicwand.repository.MagicProviderRepository;
import com.magicwand.service.MagicArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MagicArticleServiceImpl implements MagicArticleService {

    private final MagicArticleRepository articleRepository;
    private final MagicProviderRepository providerRepository;
    private final ArticleMapper articleMapper;

    @Override
    @Transactional
    public ArticleResponse create(ArticleRequest request) {
        // Validar nombre único (case-insensitive)
        if (articleRepository.existsByNameIgnoreCase(request.getName())) {
            throw new ConflictException("Ya existe un artículo con el nombre: " + request.getName());
        }

        MagicProvider provider = getProviderOrThrow(request.getProviderId());

        // Regla de negocio: el tipo del artículo debe coincidir con el tipo del proveedor
        validateProviderTypeMatch(request.getType(), provider);

        MagicArticle article = articleMapper.toEntity(request);
        article.setName(request.getName().trim());
        article.setProvider(provider);
        article = articleRepository.save(article);
        return articleMapper.toResponse(article);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArticleResponse> findAll(ArticleType category, BigDecimal maxPrice, Long providerId) {
        return articleRepository.findWithFilters(category, maxPrice, providerId)
                .stream()
                .map(articleMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ArticleResponse findById(Long id) {
        MagicArticle article = getArticleOrThrow(id);
        return articleMapper.toResponse(article);
    }

    @Override
    @Transactional
    public ArticleResponse update(Long id, ArticleRequest request) {
        MagicArticle article = getArticleOrThrow(id);

        // Validar nombre único excluyendo el actual
        articleRepository.findByNameIgnoreCase(request.getName())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new ConflictException("Ya existe un artículo con el nombre: " + request.getName());
                });

        MagicProvider provider = getProviderOrThrow(request.getProviderId());

        // Regla de negocio: el tipo del artículo debe coincidir con el tipo del proveedor
        validateProviderTypeMatch(request.getType(), provider);

        articleMapper.updateEntity(request, article);
        article.setName(request.getName().trim());
        article.setProvider(provider);
        article = articleRepository.save(article);
        return articleMapper.toResponse(article);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        MagicArticle article = getArticleOrThrow(id);
        articleRepository.delete(article);
    }

    // ── Helpers ──────────────────────────────────────────────────────────────

    private void validateProviderTypeMatch(ArticleType articleType, MagicProvider provider) {
        if (!provider.getType().equals(articleType)) {
            throw new BusinessRuleException(
                    "El proveedor '" + provider.getName() + "' es de tipo " + provider.getType() +
                    " y no puede abastecer artículos de tipo " + articleType);
        }
    }

    private MagicProvider getProviderOrThrow(Long providerId) {
        return providerRepository.findById(providerId)
                .orElseThrow(() -> new ResourceNotFoundException("Proveedor no encontrado con ID: " + providerId));
    }

    private MagicArticle getArticleOrThrow(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artículo no encontrado con ID: " + id));
    }
}
