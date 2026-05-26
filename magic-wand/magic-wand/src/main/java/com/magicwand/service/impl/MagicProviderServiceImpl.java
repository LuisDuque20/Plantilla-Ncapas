package com.magicwand.service.impl;

import com.magicwand.dto.request.ProviderRequest;
import com.magicwand.dto.response.ProviderResponse;
import com.magicwand.entity.MagicProvider;
import com.magicwand.exception.ConflictException;
import com.magicwand.exception.ResourceNotFoundException;
import com.magicwand.mapper.ProviderMapper;
import com.magicwand.repository.MagicArticleRepository;
import com.magicwand.repository.MagicProviderRepository;
import com.magicwand.service.MagicProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MagicProviderServiceImpl implements MagicProviderService {

    private final MagicProviderRepository providerRepository;
    private final MagicArticleRepository articleRepository;
    private final ProviderMapper providerMapper;

    @Override
    @Transactional
    public ProviderResponse create(ProviderRequest request) {
        // Validar nombre único (case-insensitive)
        if (providerRepository.existsByNameIgnoreCase(request.getName())) {
            throw new ConflictException("Ya existe un proveedor con el nombre: " + request.getName());
        }

        MagicProvider provider = providerMapper.toEntity(request);
        provider.setName(request.getName().trim());
        provider = providerRepository.save(provider);
        return providerMapper.toResponse(provider);
    }

    @Override
    @Transactional(readOnly = true)
    public ProviderResponse findById(Long id) {
        MagicProvider provider = getProviderOrThrow(id);
        return providerMapper.toResponse(provider);
    }

    @Override
    @Transactional
    public ProviderResponse update(Long id, ProviderRequest request) {
        MagicProvider provider = getProviderOrThrow(id);

        // Validar nombre único excluyendo el actual
        providerRepository.findByNameIgnoreCase(request.getName())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new ConflictException("Ya existe un proveedor con el nombre: " + request.getName());
                });

        providerMapper.updateEntity(request, provider);
        provider.setName(request.getName().trim());
        provider = providerRepository.save(provider);
        return providerMapper.toResponse(provider);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        MagicProvider provider = getProviderOrThrow(id);

        // Verificar que no tenga artículos asociados
        if (articleRepository.existsByProviderId(id)) {
            throw new ConflictException("No se puede eliminar el proveedor porque tiene artículos asociados. " +
                    "Elimine o reasigne los artículos primero.");
        }

        providerRepository.delete(provider);
    }

    private MagicProvider getProviderOrThrow(Long id) {
        return providerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Proveedor no encontrado con ID: " + id));
    }
}
