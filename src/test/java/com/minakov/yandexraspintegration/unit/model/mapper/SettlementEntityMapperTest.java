package com.minakov.yandexraspintegration.unit.model.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.minakov.yandexraspintegration.feign.dto.yandex.rasp.stationlist.CodeDto;
import com.minakov.yandexraspintegration.feign.dto.yandex.rasp.stationlist.SettlementDto;
import com.minakov.yandexraspintegration.feign.dto.yandex.rasp.stationlist.StationDto;
import com.minakov.yandexraspintegration.model.StationEntity;
import com.minakov.yandexraspintegration.model.mapper.SettlementEntityMapper;
import com.minakov.yandexraspintegration.model.mapper.StationEntityMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SettlementEntityMapperTest {
    private static final SettlementEntityMapper MAPPER = SettlementEntityMapper.INSTANCE;

    private static StationDto STATION_DTO;
    private static StationEntity STATION_ENTITY;

    @BeforeEach
    void beforeEach() {
        STATION_DTO = StationDto.builder()
                .title("station-title")
                .codes(CodeDto.builder().esrCode("station-esr-code").yandexCode("station-yandex-code").build())
                .direction("station-direction")
                .stationType("station-station-type")
                .transportType("station-transport-type")
                .latitude(100.001)
                .longitude(-100.0001)
                .build();

        STATION_ENTITY = StationEntityMapper.INSTANCE.map(STATION_DTO);
    }

    @Test
    void toDto_withoutNull() {
        final var source = SettlementDto.builder()
                .title("title")
                .codes(CodeDto.builder().esrCode("esr-code").yandexCode("yandex-code").build())
                .stations(List.of(STATION_DTO))
                .build();

        final var target = MAPPER.map(source);

        assertEquals(source.getTitle(), target.getTitle());
        assertEquals(source.getCodes().getEsrCode(), target.getCode().getEsrCode());
        assertEquals(source.getCodes().getYandexCode(), target.getCode().getYandexCode());

        assertNotNull(source.getStations());
        assertEquals(source.getStations().size(), target.getStations().size());
        assertEquals(STATION_ENTITY, target.getStations().get(0));
        assertEquals(target, target.getStations().get(0).getSettlement());
    }

    @Test
    void toDto_withNull() {
        final var source = SettlementDto.builder().build();

        final var target = MAPPER.map(source);

        assertNull(target.getCode());
        assertNull(target.getTitle());
        assertNull(source.getStations());
    }

    @Test
    void toDto_withBlankValues() {
        final var source = SettlementDto.builder().title("  ").stations(List.of()).build();

        final var target = MAPPER.map(source);

        assertNull(target.getCode());
        assertNull(target.getTitle());

        assertNotNull(source.getStations());
        assertTrue(source.getStations().isEmpty());
    }

    @Test
    void toDto_list() {
        final var source = SettlementDto.builder()
                .title("title")
                .codes(CodeDto.builder().esrCode("esr-code").yandexCode("yandex-code").build())
                .stations(List.of(STATION_DTO))
                .build();

        final var target = MAPPER.map(source);
        final var targets = MAPPER.map(List.of(source));

        assertEquals(1, targets.size());
        assertTrue(targets.stream().anyMatch(result -> result.equals(target)));
    }
}
