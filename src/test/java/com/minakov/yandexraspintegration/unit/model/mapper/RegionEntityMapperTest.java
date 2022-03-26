package com.minakov.yandexraspintegration.unit.model.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.minakov.yandexraspintegration.feign.dto.yandex.rasp.CodeDto;
import com.minakov.yandexraspintegration.feign.dto.yandex.rasp.RegionDto;
import com.minakov.yandexraspintegration.feign.dto.yandex.rasp.SettlementDto;
import com.minakov.yandexraspintegration.feign.dto.yandex.rasp.StationDto;
import com.minakov.yandexraspintegration.model.SettlementEntity;
import com.minakov.yandexraspintegration.model.mapper.RegionEntityMapper;
import com.minakov.yandexraspintegration.model.mapper.SettlementEntityMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegionEntityMapperTest {
    private static final RegionEntityMapper MAPPER = RegionEntityMapper.INSTANCE;

    private static SettlementDto SETTLEMENT_DTO;
    private static SettlementEntity SETTLEMENT_ENTITY;

    @BeforeEach
    void beforeEach() {
        final var station = StationDto.builder()
                .title("station-title")
                .codes(CodeDto.builder().esrCode("station-esr-code").yandexCode("station-yandex-code").build())
                .direction("station-direction")
                .stationType("station-station-type")
                .transportType("station-transport-type")
                .latitude(100.001)
                .longitude(-100.0001)
                .build();

        SETTLEMENT_DTO = SettlementDto.builder()
                .title("settlement-title")
                .codes(CodeDto.builder().esrCode("settlement-esr-code").yandexCode("settlement-yandex-code").build())
                .stations(List.of(station))
                .build();

        SETTLEMENT_ENTITY = SettlementEntityMapper.INSTANCE.map(SETTLEMENT_DTO);
    }

    @Test
    void toDto_withoutNull() {
        final var source = RegionDto.builder()
                .title("title")
                .codes(CodeDto.builder().esrCode("esr-code").yandexCode("yandex-code").build())
                .settlements(List.of(SETTLEMENT_DTO))
                .build();

        final var target = MAPPER.map(source);

        assertEquals(source.getTitle(), target.getTitle());
        assertEquals(source.getCodes().getEsrCode(), target.getCode().getEsrCode());
        assertEquals(source.getCodes().getYandexCode(), target.getCode().getYandexCode());

        assertNotNull(source.getSettlements());
        assertEquals(source.getSettlements().size(), target.getSettlements().size());
        assertEquals(SETTLEMENT_ENTITY, target.getSettlements().get(0));
        assertEquals(target, target.getSettlements().get(0).getRegion());
    }

    @Test
    void toDto_withNull() {
        final var source = RegionDto.builder().build();

        final var target = MAPPER.map(source);

        assertNull(target.getCode());
        assertNull(target.getTitle());
        assertNull(source.getSettlements());
    }

    @Test
    void toDto_withBlankValues() {
        final var source = RegionDto.builder().title("  ").settlements(List.of()).build();

        final var target = MAPPER.map(source);

        assertNull(target.getCode());
        assertNull(target.getTitle());

        assertNotNull(source.getSettlements());
        assertTrue(source.getSettlements().isEmpty());
    }

    @Test
    void toDto_list() {
        final var source = RegionDto.builder()
                .title("title")
                .codes(CodeDto.builder().esrCode("esr-code").yandexCode("yandex-code").build())
                .settlements(List.of(SETTLEMENT_DTO))
                .build();

        final var target = MAPPER.map(source);
        final var targets = MAPPER.map(List.of(source));

        assertEquals(1, targets.size());
        assertTrue(targets.stream().anyMatch(result -> result.equals(target)));
    }
}
