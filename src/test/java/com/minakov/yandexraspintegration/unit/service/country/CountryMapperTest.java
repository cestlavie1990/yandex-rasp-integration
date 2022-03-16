package com.minakov.yandexraspintegration.unit.service.country;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.minakov.yandexraspintegration.model.CountryEntity;
import com.minakov.yandexraspintegration.model.embedded.CodeEmbedded;
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
    void toDto_withNull() {
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

    @Test
    void toDto_list() {
        final var entity1 = CountryEntity.builder()
                .id(UUID.randomUUID())
                .title("test-title1")
                .code(CodeEmbedded.builder().yandexCode("test-yandex-code1").esrCode("test-esr-code1").build())
                .build();
        final var entity2 = CountryEntity.builder()
                .id(UUID.randomUUID())
                .title("test-title2")
                .code(CodeEmbedded.builder().build())
                .build();

        final var dto1 = MAPPER.toDto(entity1);
        final var dto2 = MAPPER.toDto(entity2);

        final var results = MAPPER.toDto(List.of(entity1, entity2));

        assertEquals(2, results.size());

        assertTrue(results.stream().anyMatch(result -> result.equals(dto1)));
        assertTrue(results.stream().anyMatch(result -> result.equals(dto2)));
    }
}
