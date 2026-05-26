package com.magicwand.controller;

import com.magicwand.dto.request.ProviderRequest;
import com.magicwand.dto.response.ProviderResponse;
import com.magicwand.service.MagicProviderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/providers")
@RequiredArgsConstructor
public class ProviderController {

    private final MagicProviderService providerService;

    @PostMapping
    public ResponseEntity<ProviderResponse> create(@Valid @RequestBody ProviderRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(providerService.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProviderResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(providerService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProviderResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody ProviderRequest request) {
        return ResponseEntity.ok(providerService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        providerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
