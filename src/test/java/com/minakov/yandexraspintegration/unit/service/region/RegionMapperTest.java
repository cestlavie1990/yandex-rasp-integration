package com.minakov.yandexraspintegration.unit.service.region;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.minakov.yandexraspintegration.model.CountryEntity;
import com.minakov.yandexraspintegration.model.RegionEntity;
import com.minakov.yandexraspintegration.model.embedded.CodeEmbedded;
import com.minakov.yandexraspintegration.service.region.RegionMapper;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegionMapperTest {
    private static final RegionMapper MAPPER = RegionMapper.INSTANCE;

    private static CountryEntity COUNTRY;

    @BeforeEach
    void beforeEach() {
        COUNTRY = CountryEntity.builder()
                .id(UUID.randomUUID())
                .title("country-title")
                .code(CodeEmbedded.builder().yandexCode("country-yandex-code").esrCode("country-esr-code").build())
                .build();
    }

    @Test
    void toDto_withoutNull() {
        final var entity = RegionEntity.builder()
                .id(UUID.randomUUID())
                .country(COUNTRY)
                .countryId(COUNTRY.getId())
                .title("region-title")
                .code(CodeEmbedded.builder().yandexCode("region-yandex-code").esrCode("region-esr-code").build())
                .build();

        final var dto = MAPPER.map(entity);

        assertNotNull(dto.getCode());
        assertEquals(entity.getId().toString(), dto.getId());
        assertEquals(entity.getTitle(), dto.getTitle());
        assertEquals(entity.getCode().getYandexCode(), dto.getCode().getYandexCode());
        assertEquals(entity.getCode().getEsrCode(), dto.getCode().getEsrCode());
        assertEquals(entity.getCountry().getId().toString(), dto.getCountryId());
    }

    @Test
    void toDto_withNull() {
        final var entity =
                RegionEntity.builder().id(UUID.randomUUID()).country(COUNTRY).countryId(COUNTRY.getId()).build();

        final var dto = MAPPER.map(entity);

        assertEquals(entity.getId().toString(), dto.getId());
        assertEquals(entity.getCountry().getId().toString(), dto.getCountryId());
        assertNull(dto.getCode());
        assertNull(dto.getTitle());
    }

    @Test
    void toDto_list() {
        final var entity = RegionEntity.builder()
                .id(UUID.randomUUID())
                .country(COUNTRY)
                .countryId(COUNTRY.getId())
                .title("region-title")
                .code(CodeEmbedded.builder().build())
                .build();

        final var dto = MAPPER.map(entity);

        final var results = MAPPER.map(List.of(entity));

        assertEquals(1, results.size());
        assertTrue(results.stream().anyMatch(result -> result.equals(dto)));
    }
}
