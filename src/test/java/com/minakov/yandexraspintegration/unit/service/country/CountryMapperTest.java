package com.minakov.yandexraspintegration.unit.service.country;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.minakov.yandexraspintegration.model.CountryEntity;
import com.minakov.yandexraspintegration.model.embedded.CodeEmbedded;
import com.minakov.yandexraspintegration.service.country.CountryMapper;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class CountryMapperTest {
    private static final CountryMapper MAPPER = CountryMapper.INSTANCE;

    @Test
    void map_withoutNull() {
        final var entity = CountryEntity.builder()
                .id(UUID.randomUUID())
                .title("test-title")
                .code(CodeEmbedded.builder().yandexCode("test-yandex-code").esrCode("test-esr-code").build())
                .build();

        final var dto = MAPPER.toDto(entity);

        assertEquals(entity.getId().toString(), dto.getId());
        assertEquals(entity.getTitle(), dto.getTitle());
        assertEquals(entity.getCode().getYandexCode(), dto.getCode().getYandexCode());
        assertEquals(entity.getCode().getEsrCode(), dto.getCode().getEsrCode());
    }

    @Test
    void map_withNull() {
        final var entity = CountryEntity.builder()
                .id(UUID.randomUUID())
                .title("test-title")
                .code(CodeEmbedded.builder().build())
                .build();

        final var dto = MAPPER.toDto(entity);

        assertEquals(entity.getId().toString(), dto.getId());
        assertEquals(entity.getTitle(), dto.getTitle());
        assertNull(dto.getCode().getYandexCode());
        assertNull(dto.getCode().getEsrCode());
    }
}
