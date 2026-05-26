package com.magicwand.repository;

import com.magicwand.entity.MagicArticle;
import com.magicwand.enums.ArticleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface MagicArticleRepository extends JpaRepository<MagicArticle, Long> {

    Optional<MagicArticle> findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);

    boolean existsByProviderId(Long providerId);

    @Query("SELECT a FROM MagicArticle a WHERE " +
            "(:category IS NULL OR a.type = :category) AND " +
            "(:maxPrice IS NULL OR a.price <= :maxPrice) AND " +
            "(:providerId IS NULL OR a.provider.id = :providerId)")
    List<MagicArticle> findWithFilters(
            @Param("category") ArticleType category,
            @Param("maxPrice") BigDecimal maxPrice,
            @Param("providerId") Long providerId
    );
}
