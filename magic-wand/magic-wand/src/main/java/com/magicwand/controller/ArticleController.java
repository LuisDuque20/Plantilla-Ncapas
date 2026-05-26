package com.magicwand.controller;

import com.magicwand.dto.request.ArticleRequest;
import com.magicwand.dto.response.ArticleResponse;
import com.magicwand.enums.ArticleType;
import com.magicwand.service.MagicArticleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/artefacts")
@RequiredArgsConstructor
public class ArticleController {

    private final MagicArticleService articleService;

    @PostMapping
    public ResponseEntity<ArticleResponse> create(@Valid @RequestBody ArticleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(articleService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<ArticleResponse>> findAll(
            @RequestParam(required = false) ArticleType category,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) Long provider) {
        return ResponseEntity.ok(articleService.findAll(category, maxPrice, provider));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(articleService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArticleResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody ArticleRequest request) {
        return ResponseEntity.ok(articleService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        articleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
