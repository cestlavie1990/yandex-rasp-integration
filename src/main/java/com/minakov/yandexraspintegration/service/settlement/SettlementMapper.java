package com.minakov.yandexraspintegration.service.settlement;

import com.minakov.yandexraspintegration.controller.graphql.type.code.Code;
import com.minakov.yandexraspintegration.controller.graphql.type.settlement.Settlement;
import com.minakov.yandexraspintegration.model.SettlementEntity;
import com.minakov.yandexraspintegration.model.embedded.CodeEmbedded;
import com.minakov.yandexraspintegration.service.IMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SettlementMapper extends IMapper<SettlementEntity, Settlement> {
    SettlementMapper INSTANCE = Mappers.getMapper(SettlementMapper.class);

    @Override
    @Mappings({
            @Mapping(target = "id", expression = "java(source.getId().toString())"),
            @Mapping(target = "regionId", expression = "java(source.getRegionId().toString())")
    })
    Settlement map(final SettlementEntity source);

    default Code mapCode(CodeEmbedded value) {
        final var builder = Code.builder();
        return value == null ? builder.build() :
                builder.esrCode(value.getEsrCode()).yandexCode(value.getYandexCode()).build();
    }
}
