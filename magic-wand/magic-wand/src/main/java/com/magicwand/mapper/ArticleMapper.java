package com.magicwand.mapper;

import com.magicwand.dto.request.ArticleRequest;
import com.magicwand.dto.response.ArticleResponse;
import com.magicwand.entity.MagicArticle;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {ProviderMapper.class})
public interface ArticleMapper {

    @Mapping(target = "provider", ignore = true)
    MagicArticle toEntity(ArticleRequest request);

    ArticleResponse toResponse(MagicArticle article);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "provider", ignore = true)
    void updateEntity(ArticleRequest request, @MappingTarget MagicArticle article);
}
