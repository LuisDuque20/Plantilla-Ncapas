package com.magicwand.service;

import com.magicwand.dto.request.ProviderRequest;
import com.magicwand.dto.response.ProviderResponse;

public interface MagicProviderService {

    ProviderResponse create(ProviderRequest request);

    ProviderResponse findById(Long id);

    ProviderResponse update(Long id, ProviderRequest request);

    void delete(Long id);
}
