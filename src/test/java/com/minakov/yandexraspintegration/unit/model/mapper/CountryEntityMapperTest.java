package com.minakov.yandexraspintegration.unit.model.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.minakov.yandexraspintegration.feign.dto.yandex.rasp.stationlist.CodeDto;
import com.minakov.yandexraspintegration.feign.dto.yandex.rasp.stationlist.CountryDto;
import com.minakov.yandexraspintegration.feign.dto.yandex.rasp.stationlist.RegionDto;
import com.minakov.yandexraspintegration.feign.dto.yandex.rasp.stationlist.SettlementDto;
import com.minakov.yandexraspintegration.feign.dto.yandex.rasp.stationlist.StationDto;
import com.minakov.yandexraspintegration.model.RegionEntity;
import com.minakov.yandexraspintegration.model.mapper.CountryEntityMapper;
import com.minakov.yandexraspintegration.model.mapper.RegionEntityMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CountryEntityMapperTest {
    private static final CountryEntityMapper MAPPER = CountryEntityMapper.INSTANCE;

    private static RegionDto REGION_DTO;
    private static RegionEntity REGION_ENTITY;

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

        final var settlement = SettlementDto.builder()
                .title("settlement-title")
                .codes(CodeDto.builder().esrCode("settlement-esr-code").yandexCode("settlement-yandex-code").build())
                .stations(List.of(station))
                .build();

        REGION_DTO = RegionDto.builder()
                .title("region-title")
                .codes(CodeDto.builder().esrCode("region-esr-code").yandexCode("region-yandex-code").build())
                .settlements(List.of(settlement))
                .build();

        REGION_ENTITY = RegionEntityMapper.INSTANCE.map(REGION_DTO);
    }

    @Test
    void toDto_withoutNull() {
        final var source = CountryDto.builder()
                .title("title")
                .codes(CodeDto.builder().esrCode("esr-code").yandexCode("yandex-code").build())
                .regions(List.of(REGION_DTO))
                .build();

        final var target = MAPPER.map(source);

        assertEquals(source.getTitle(), target.getTitle());
        assertEquals(source.getCodes().getEsrCode(), target.getCode().getEsrCode());
        assertEquals(source.getCodes().getYandexCode(), target.getCode().getYandexCode());

        assertNotNull(source.getRegions());
        assertEquals(source.getRegions().size(), target.getRegions().size());
        assertEquals(REGION_ENTITY, target.getRegions().get(0));
        assertEquals(target, target.getRegions().get(0).getCountry());
    }

    @Test
    void toDto_withNull() {
        final var source = CountryDto.builder().build();

        final var target = MAPPER.map(source);

        assertNull(target.getCode());
        assertNull(target.getTitle());
        assertNull(source.getRegions());
    }

    @Test
    void toDto_withBlankValues() {
        final var source = CountryDto.builder().title("").regions(List.of()).build();

        final var target = MAPPER.map(source);

        assertNull(target.getCode());
        assertNull(target.getTitle());

        assertNotNull(source.getRegions());
        assertTrue(source.getRegions().isEmpty());
    }

    @Test
    void toDto_list() {
        final var source = CountryDto.builder()
                .title("title")
                .codes(CodeDto.builder().esrCode("esr-code").yandexCode("yandex-code").build())
                .regions(List.of(REGION_DTO))
                .build();

        final var target = MAPPER.map(source);
        final var targets = MAPPER.map(List.of(source));

        assertEquals(1, targets.size());
        assertTrue(targets.stream().anyMatch(result -> result.equals(target)));
    }
}
