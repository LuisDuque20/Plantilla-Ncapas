package com.magicwand.controller;

import com.magicwand.model.dto.request.ProviderRequest;
import com.magicwand.model.dto.response.ProviderResponse;
import com.magicwand.service.MagicProviderService;
import com.magicwand.util.ApiResponse;
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
    public ResponseEntity<ApiResponse<ProviderResponse>> create(
            @Valid @RequestBody ProviderRequest request) {
        ProviderResponse response = providerService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProviderResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(providerService.findById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProviderResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody ProviderRequest request) {
        return ResponseEntity.ok(ApiResponse.success(providerService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        providerService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.deleted());
    }
}
