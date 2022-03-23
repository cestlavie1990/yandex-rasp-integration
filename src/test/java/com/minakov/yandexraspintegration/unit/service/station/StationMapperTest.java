package com.minakov.yandexraspintegration.unit.service.station;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.minakov.yandexraspintegration.model.CountryEntity;
import com.minakov.yandexraspintegration.model.RegionEntity;
import com.minakov.yandexraspintegration.model.SettlementEntity;
import com.minakov.yandexraspintegration.model.StationEntity;
import com.minakov.yandexraspintegration.model.embedded.CodeEmbedded;
import com.minakov.yandexraspintegration.service.station.StationMapper;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StationMapperTest {
    private static final StationMapper MAPPER = StationMapper.INSTANCE;

    private static SettlementEntity SETTLEMENT;

    @BeforeEach
    void beforeEach() {
        final var country = CountryEntity.builder()
                .id(UUID.randomUUID())
                .title("country-title")
                .code(CodeEmbedded.builder().yandexCode("country-yandex-code").esrCode("country-esr-code").build())
                .build();

        final var region = RegionEntity.builder()
                .id(UUID.randomUUID())
                .country(country)
                .countryId(country.getId())
                .title("region-title")
                .code(CodeEmbedded.builder().yandexCode("region-yandex-code").esrCode("region-esr-code").build())
                .build();

        SETTLEMENT = SettlementEntity.builder()
                .id(UUID.randomUUID())
                .region(region)
                .regionId(region.getId())
                .title("settlement-title")
                .code(CodeEmbedded.builder()
                        .yandexCode("settlement-yandex-code")
                        .esrCode("settlement-esr-code")
                        .build())
                .build();
    }

    @Test
    void toDto_withoutNull() {
        final var entity = StationEntity.builder()
                .id(UUID.randomUUID())
                .settlement(SETTLEMENT)
                .settlementId(SETTLEMENT.getId())
                .title("station-title")
                .code(CodeEmbedded.builder().yandexCode("station-yandex-code").esrCode("station-esr-code").build())
                .direction("station-direction")
                .stationType("station-station-type")
                .transportType("station-transport-type")
                .latitude(150.123)
                .longitude(-11.4567)
                .build();

        final var dto = MAPPER.toDto(entity);

        assertEquals(entity.getId().toString(), dto.getId());
        assertEquals(entity.getTitle(), dto.getTitle());
        assertEquals(entity.getCode().getYandexCode(), dto.getCode().getYandexCode());
        assertEquals(entity.getCode().getEsrCode(), dto.getCode().getEsrCode());
        assertEquals(entity.getSettlement().getId().toString(), dto.getSettlementId());
    }

    @Test
    void toDto_withNull() {
        final var entity = StationEntity.builder()
                .id(UUID.randomUUID())
                .settlement(SETTLEMENT)
                .settlementId(SETTLEMENT.getId())
                .title("station-title")
                .code(CodeEmbedded.builder().build())
                .direction("station-direction")
                .stationType("station-station-type")
                .transportType("station-transport-type")
                .latitude(150.123)
                .longitude(-11.4567)
                .build();

        final var dto = MAPPER.toDto(entity);

        assertEquals(entity.getId().toString(), dto.getId());
        assertEquals(entity.getTitle(), dto.getTitle());
        assertEquals(entity.getSettlement().getId().toString(), dto.getSettlementId());
        assertNull(dto.getCode().getYandexCode());
        assertNull(dto.getCode().getEsrCode());
    }

    @Test
    void toDto_list() {
        final var entity = StationEntity.builder()
                .id(UUID.randomUUID())
                .settlement(SETTLEMENT)
                .settlementId(SETTLEMENT.getId())
                .title("station-title")
                .code(CodeEmbedded.builder().yandexCode("station-yandex-code").esrCode("station-esr-code").build())
                .direction("station-direction")
                .stationType("station-station-type")
                .transportType("station-transport-type")
                .latitude(150.123)
                .longitude(-11.4567)
                .build();

        final var dto = MAPPER.toDto(entity);

        final var results = MAPPER.toDto(List.of(entity));

        assertEquals(1, results.size());
        assertTrue(results.stream().anyMatch(result -> result.equals(dto)));
    }
}
