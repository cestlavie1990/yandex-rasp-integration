package com.minakov.yandexraspintegration.unit.service.country;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.minakov.yandexraspintegration.model.CountryEntity;
import com.minakov.yandexraspintegration.model.embedded.CodeEmbedded;
import com.minakov.yandexraspintegration.model.embedded.YandexRaspKey;
import com.minakov.yandexraspintegration.service.country.CountryMapper;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class CountryMapperTest {
    private static final CountryMapper MAPPER = CountryMapper.INSTANCE;

    @Test
    void toDto_withoutNull() {
        final var entity = CountryEntity.builder()
                .id(UUID.randomUUID())
                .yandexRaspKey(YandexRaspKey.builder()
                        .title("test-title")
                        .code(CodeEmbedded.builder().yandexCode("test-yandex-code").esrCode("test-esr-code").build())
                        .build())
                .build();

        final var dto = MAPPER.map(entity);

        assertEquals(entity.getId().toString(), dto.getId());
        assertEquals(entity.getYandexRaspKey().getTitle(), dto.getTitle());
        assertEquals(entity.getYandexRaspKey().getCode().getYandexCode(), dto.getCode().getYandexCode());
        assertEquals(entity.getYandexRaspKey().getCode().getEsrCode(), dto.getCode().getEsrCode());
    }

    @Test
    void toDto_withNull() {
        final var entity = CountryEntity.builder()
                .id(UUID.randomUUID())
                .yandexRaspKey(YandexRaspKey.builder()
                        .title("test-title")
                        .code(CodeEmbedded.builder().build())
                        .build())
                .build();

        final var dto = MAPPER.map(entity);

        assertEquals(entity.getId().toString(), dto.getId());
        assertEquals(entity.getYandexRaspKey().getTitle(), dto.getTitle());
        assertNull(dto.getCode().getYandexCode());
        assertNull(dto.getCode().getEsrCode());
    }

    @Test
    void toDto_list() {
        final var entity = CountryEntity.builder()
                .id(UUID.randomUUID())
                .yandexRaspKey(YandexRaspKey.builder()
                        .title("test-title")
                        .code(CodeEmbedded.builder().yandexCode("test-yandex-code").esrCode("test-esr-code").build())
                        .build())
                .build();

        final var dto = MAPPER.map(entity);

        final var results = MAPPER.map(List.of(entity));

        assertEquals(1, results.size());
        assertTrue(results.stream().anyMatch(result -> result.equals(dto)));
    }
}
