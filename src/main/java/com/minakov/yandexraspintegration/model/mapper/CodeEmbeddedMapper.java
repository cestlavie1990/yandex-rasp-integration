package com.minakov.yandexraspintegration.model.mapper;

import com.minakov.yandexraspintegration.feign.dto.yandex.rasp.stationlist.CodeDto;
import com.minakov.yandexraspintegration.model.embedded.CodeEmbedded;
import com.minakov.yandexraspintegration.service.IMapper;
import com.minakov.yandexraspintegration.util.MapperUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(uses = MapperUtils.class)
public interface CodeEmbeddedMapper extends IMapper<CodeDto, CodeEmbedded> {
    CodeEmbeddedMapper INSTANCE = Mappers.getMapper(CodeEmbeddedMapper.class);

    @Override
    @Mappings({
            @Mapping(qualifiedByName = "NullIfBlank", target = "esrCode"),
            @Mapping(qualifiedByName = "NullIfBlank", target = "yandexCode")
    })
    CodeEmbedded map(final CodeDto source);
}
