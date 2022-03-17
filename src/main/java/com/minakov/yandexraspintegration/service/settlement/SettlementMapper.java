package com.minakov.yandexraspintegration.service.settlement;

import com.minakov.yandexraspintegration.controller.graphql.type.settlement.Settlement;
import com.minakov.yandexraspintegration.model.SettlementEntity;
import com.minakov.yandexraspintegration.service.IMapper;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SettlementMapper extends IMapper<SettlementEntity, Settlement> {
    SettlementMapper INSTANCE = Mappers.getMapper(SettlementMapper.class);

    @Override
    @Mappings({
            @Mapping(target = "regionId", source = "source.region.id")
    })
    Settlement toDto(final SettlementEntity source);

    default String map(final UUID id) {
        return id == null ? null : id.toString();
    }
}
