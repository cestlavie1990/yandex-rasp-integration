package com.minakov.yandexraspintegration.unit.service.flight;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.minakov.yandexraspintegration.controller.graphql.type.schedule.TransportType;
import com.minakov.yandexraspintegration.feign.dto.yandex.rasp.schedulestation.ScheduleDto;
import com.minakov.yandexraspintegration.feign.dto.yandex.rasp.schedulestation.ThreadDto;
import com.minakov.yandexraspintegration.service.flight.FlightMapper;
import java.time.OffsetDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;

class FlightMapperTest {
    private static final FlightMapper MAPPER = FlightMapper.INSTANCE;

    @Test
    void map_withoutNull() {
        final var source = ScheduleDto.builder()
                .thread(ThreadDto.builder()
                        .uid("flight-uid")
                        .title("flight-title")
                        .number("flight-number")
                        .transportType("plane")
                        .vehicle("flight-vehicle")
                        .build())
                .arrival(OffsetDateTime.parse("2022-04-02T20:30:00+03:00"))
                .departure(OffsetDateTime.parse("2022-04-02T23:30:00+03:00"))
                .isFuzzy(true)
                .terminal("flight-terminal")
                .platform("flight-platform")
                .build();

        final var target = MAPPER.map(source);

        assertEquals(source.getThread().getUid(), target.getUid());
        assertEquals(source.getThread().getTitle(), target.getTitle());
        assertEquals(source.getThread().getNumber(), target.getNumber());
        assertEquals(source.getThread().getVehicle(), target.getVehicle());
        assertEquals(source.getTerminal(), target.getTerminal());
        assertEquals(source.getPlatform(), target.getPlatform());
        assertEquals(source.getArrival(), target.getArrival());
        assertEquals(source.getDeparture(), target.getDeparture());
        assertEquals(source.getIsFuzzy(), target.getIsFuzzy());
        assertNotNull(source.getThread().getTransportType());
        assertEquals(TransportType.byInternalName(source.getThread().getTransportType()), target.getTransportType());
    }

    @Test
    void map_witNull() {
        final var source = ScheduleDto.builder().thread(ThreadDto.builder().uid("flight-uid").build()).build();

        final var target = MAPPER.map(source);

        assertNull(target.getTitle());
        assertNull(target.getNumber());
        assertNull(target.getVehicle());
        assertNull(target.getTerminal());
        assertNull(target.getPlatform());
        assertNull(target.getArrival());
        assertNull(target.getDeparture());
        assertNull(target.getIsFuzzy());
        assertNull(target.getTransportType());
        assertEquals(source.getThread().getUid(), target.getUid());
    }

    @Test
    void map_list() {
        final var source = ScheduleDto.builder()
                .thread(ThreadDto.builder()
                        .uid("flight-uid")
                        .title("flight-title")
                        .number("flight-number")
                        .transportType("plane")
                        .vehicle("flight-vehicle")
                        .build())
                .arrival(OffsetDateTime.parse("2022-04-02T20:30:00+03:00"))
                .departure(OffsetDateTime.parse("2022-04-02T23:30:00+03:00"))
                .isFuzzy(true)
                .terminal("flight-terminal")
                .platform("flight-platform")
                .build();

        final var target = MAPPER.map(source);

        final var results = MAPPER.map(List.of(source));

        assertEquals(1, results.size());
        assertTrue(results.stream().anyMatch(result -> result.equals(target)));
    }
}
