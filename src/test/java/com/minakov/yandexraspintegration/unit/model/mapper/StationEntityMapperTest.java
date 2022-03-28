package com.minakov.yandexraspintegration.unit.model.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.minakov.yandexraspintegration.feign.dto.yandex.rasp.stationlist.CodeDto;
import com.minakov.yandexraspintegration.feign.dto.yandex.rasp.stationlist.StationDto;
import com.minakov.yandexraspintegration.model.mapper.StationEntityMapper;
import java.util.List;
import org.junit.jupiter.api.Test;

class StationEntityMapperTest {
    private static final StationEntityMapper MAPPER = StationEntityMapper.INSTANCE;

    @Test
    void toDto_withoutNull() {
        final var source = StationDto.builder()
                .title("title")
                .codes(CodeDto.builder().esrCode("esr-code").yandexCode("yandex-code").build())
                .direction("direction")
                .stationType("station-type")
                .transportType("transport-type")
                .latitude(100.001)
                .longitude(-100.0001)
                .build();

        final var target = MAPPER.map(source);

        assertEquals(source.getTitle(), target.getTitle());
        assertEquals(source.getDirection(), target.getDirection());
        assertEquals(source.getStationType(), target.getStationType());
        assertEquals(source.getTransportType(), target.getTransportType());
        assertEquals(source.getCodes().getEsrCode(), target.getCode().getEsrCode());
        assertEquals(source.getCodes().getYandexCode(), target.getCode().getYandexCode());
        assertEquals(source.getLatitude(), target.getLatitude());
        assertEquals(source.getLongitude(), target.getLongitude());
    }

    @Test
    void toDto_withNull() {
        final var source = StationDto.builder().build();

        final var target = MAPPER.map(source);

        assertNull(target.getCode());
        assertNull(target.getTitle());
        assertNull(target.getLatitude());
        assertNull(target.getLongitude());
        assertNull(target.getDirection());
        assertNull(target.getStationType());
        assertNull(target.getTransportType());
    }

    @Test
    void toDto_withBlankValues() {
        final var source =
                StationDto.builder().title("").direction(" ").stationType("  ").transportType("    ").build();

        final var target = MAPPER.map(source);

        assertNull(target.getCode());
        assertNull(target.getTitle());
        assertNull(target.getLatitude());
        assertNull(target.getLongitude());
        assertNull(target.getDirection());
        assertNull(target.getStationType());
        assertNull(target.getTransportType());
    }

    @Test
    void toDto_list() {
        final var source = StationDto.builder()
                .title("title")
                .codes(CodeDto.builder().esrCode("").yandexCode("   ").build())
                .direction("direction")
                .stationType("station-type")
                .transportType("transport-type")
                .latitude(100.001)
                .longitude(-100.0001)
                .build();

        final var target = MAPPER.map(source);
        final var targets = MAPPER.map(List.of(source));

        assertEquals(1, targets.size());
        assertTrue(targets.stream().anyMatch(result -> result.equals(target)));
    }
}
