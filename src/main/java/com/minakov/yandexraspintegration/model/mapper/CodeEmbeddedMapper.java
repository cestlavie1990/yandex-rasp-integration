package com.minakov.yandexraspintegration.model.mapper;

import com.minakov.yandexraspintegration.feign.dto.yandex.rasp.CodeDto;
import com.minakov.yandexraspintegration.model.embedded.CodeEmbedded;
import com.minakov.yandexraspintegration.service.IMapper;
import org.mapstruct.Mapper;

@Mapper
public interface CodeEmbeddedMapper extends IMapper<CodeDto, CodeEmbedded> {}
