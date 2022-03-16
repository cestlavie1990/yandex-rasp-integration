package com.minakov.yandexraspintegration.unit.service.region;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.minakov.yandexraspintegration.model.CountryEntity;
import com.minakov.yandexraspintegration.model.RegionEntity;
import com.minakov.yandexraspintegration.model.embedded.CodeEmbedded;
import com.minakov.yandexraspintegration.service.region.RegionMapper;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class RegionMapperTest {
    private static final RegionMapper MAPPER = RegionMapper.INSTANCE;

    @Test
    void toDto_withoutNull() {
        final var country = CountryEntity.builder()
                .id(UUID.randomUUID())
                .title("country-title")
                .code(CodeEmbedded.builder().yandexCode("country-yandex-code").esrCode("country-esr-code").build())
                .build();

        final var entity = RegionEntity.builder()
                .id(UUID.randomUUID())
                .country(country)
                .title("region-title")
                .code(CodeEmbedded.builder().yandexCode("region-yandex-code").esrCode("region-esr-code").build())
                .build();

        final var dto = MAPPER.toDto(entity);

        assertEquals(entity.getId().toString(), dto.getId());
        assertEquals(entity.getTitle(), dto.getTitle());
        assertEquals(entity.getCode().getYandexCode(), dto.getCode().getYandexCode());
        assertEquals(entity.getCode().getEsrCode(), dto.getCode().getEsrCode());
        assertEquals(entity.getCountry().getId(), dto.getCountryId());
    }

    @Test
    void toDto_withNull() {
        final var country = CountryEntity.builder()
                .id(UUID.randomUUID())
                .title("country-title")
                .code(CodeEmbedded.builder().yandexCode("country-yandex-code").esrCode("country-esr-code").build())
                .build();

        final var entity = RegionEntity.builder()
                .id(UUID.randomUUID())
                .country(country)
                .title("region-title")
                .code(CodeEmbedded.builder().build())
                .build();

        final var dto = MAPPER.toDto(entity);

        assertEquals(entity.getId().toString(), dto.getId());
        assertEquals(entity.getTitle(), dto.getTitle());
        assertEquals(entity.getCountry().getId(), dto.getCountryId());
        assertNull(dto.getCode().getYandexCode());
        assertNull(dto.getCode().getEsrCode());
    }

    @Test
    void toDto_list() {
        final var country1 = CountryEntity.builder()
                .id(UUID.randomUUID())
                .title("country-title1")
                .code(CodeEmbedded.builder().yandexCode("country-yandex-code1").esrCode("country-esr-code1").build())
                .build();
        final var country2 = CountryEntity.builder()
                .id(UUID.randomUUID())
                .title("country-title2")
                .code(CodeEmbedded.builder().yandexCode("country-yandex-code2").esrCode("country-esr-code2").build())
                .build();

        final var entity1 = RegionEntity.builder()
                .id(UUID.randomUUID())
                .country(country1)
                .title("region-title1")
                .code(CodeEmbedded.builder().yandexCode("region-yandex-code1").esrCode("region-esr-code1").build())
                .build();
        final var entity2 = RegionEntity.builder()
                .id(UUID.randomUUID())
                .country(country2)
                .title("region-title2")
                .code(CodeEmbedded.builder().yandexCode("region-yandex-code2").esrCode("region-esr-code2").build())
                .build();

        final var dto1 = MAPPER.toDto(entity1);
        final var dto2 = MAPPER.toDto(entity2);

        final var results = MAPPER.toDto(List.of(entity1, entity2));

        assertEquals(2, results.size());

        assertTrue(results.stream().anyMatch(result -> result.equals(dto1)));
        assertTrue(results.stream().anyMatch(result -> result.equals(dto2)));
    }
}
