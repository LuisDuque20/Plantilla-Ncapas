package com.magicwand.mapper;

import com.magicwand.dto.request.ProviderRequest;
import com.magicwand.dto.response.ProviderResponse;
import com.magicwand.entity.MagicProvider;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProviderMapper {

    MagicProvider toEntity(ProviderRequest request);

    ProviderResponse toResponse(MagicProvider provider);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(ProviderRequest request, @MappingTarget MagicProvider provider);
}
