package com.magicwand.controller;

import com.magicwand.model.dto.request.ArticleRequest;
import com.magicwand.model.dto.response.ArticleResponse;
import com.magicwand.enums.ArticleType;
import com.magicwand.service.MagicArticleService;
import com.magicwand.util.ApiResponse;
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
    public ResponseEntity<ApiResponse<ArticleResponse>> create(
            @Valid @RequestBody ArticleRequest request) {
        ArticleResponse response = articleService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ArticleResponse>>> findAll(
            @RequestParam(required = false) ArticleType category,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) Long provider) {
        return ResponseEntity.ok(ApiResponse.success(articleService.findAll(category, maxPrice, provider)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ArticleResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(articleService.findById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ArticleResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody ArticleRequest request) {
        return ResponseEntity.ok(ApiResponse.success(articleService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        articleService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.deleted());
    }
}
