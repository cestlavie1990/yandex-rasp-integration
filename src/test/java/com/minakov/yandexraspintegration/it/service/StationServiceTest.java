package com.minakov.yandexraspintegration.it.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.minakov.yandexraspintegration.exception.StationNotFoundException;
import com.minakov.yandexraspintegration.it.AbstractIT;
import com.minakov.yandexraspintegration.it.SpringBootIT;
import com.minakov.yandexraspintegration.it.helper.StationTestHelper;
import com.minakov.yandexraspintegration.model.StationEntity;
import com.minakov.yandexraspintegration.service.station.StationService;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootIT
class StationServiceTest extends AbstractIT {
    @Autowired
    private StationService service;
    @Autowired
    private StationTestHelper helper;

    private static final Data DATA = new Data();

    @BeforeEach
    void beforeEach() {
        if (!DATA.isInited()) {
            DATA.setStationId1(helper.create());
            DATA.setStationId2(helper.create());
            DATA.setStationId3(helper.create());
            DATA.setInited(true);
        }
    }

    @Test
    void getEntityByIdLocked() {
        final var result = service.getEntityByIdLocked(DATA.getStationId1());

        assertEquals(DATA.getStationId1(), result.getId());
        assertThrows(StationNotFoundException.class, () -> service.getEntityByIdLocked(FAKE_UUID));
    }

    @Test
    void getEntityById() {
        final var result = service.getEntityById(DATA.getStationId1());

        assertEquals(DATA.getStationId1(), result.getId());
        assertThrows(StationNotFoundException.class, () -> service.getEntityById(FAKE_UUID));
    }

    @Test
    void getByIdOptional() {
        {
            final var result = service.getByIdOptional(DATA.getStationId1());

            assertTrue(result.isPresent());
            assertEquals(DATA.getStationId1().toString(), result.get().getId());

            assertTrue(service.getByIdOptional(FAKE_UUID).isEmpty());
        }

        {
            final var result = service.getByIdOptional(DATA.getStationId1(), StationEntity::getId);

            assertTrue(result.isPresent());
            assertEquals(DATA.getStationId1(), result.get());

            assertTrue(service.getByIdOptional(FAKE_UUID, StationEntity::getId).isEmpty());
        }
    }

    @Test
    void getById() {
        {
            final var result = service.getById(DATA.getStationId1());

            assertEquals(DATA.getStationId1().toString(), result.getId());
            assertThrows(StationNotFoundException.class, () -> service.getById(FAKE_UUID));
        }
        {
            final var result = service.getById(DATA.getStationId1(), StationEntity::getId);

            assertEquals(DATA.getStationId1(), result);
            assertThrows(StationNotFoundException.class, () -> service.getById(FAKE_UUID, StationEntity::getId));
        }
    }

    @Test
    void existsById() {
        assertFalse(service.existsById(FAKE_UUID));
        assertTrue(service.existsById(DATA.getStationId1()));
    }

    @Test
    void getAllById() {
        {
            final var results = service.getAllById(Set.of(DATA.getStationId2(), DATA.getStationId3()));

            assertEquals(2, results.size());
            assertTrue(results.stream().anyMatch(result -> result.getId().equals(DATA.getStationId2().toString())));
            assertTrue(results.stream().anyMatch(result -> result.getId().equals(DATA.getStationId3().toString())));

            assertTrue(service.getAllById(Set.of(FAKE_UUID)).isEmpty());

            assertTrue(service.getAllById(Set.of()).isEmpty());
        }
        {
            final var results = service.getAllById(Set.of(FAKE_UUID, DATA.getStationId1()));

            assertEquals(1, results.size());
            assertTrue(results.stream().anyMatch(result -> result.getId().equals(DATA.getStationId1().toString())));
        }
    }

    @Test
    void getAllById_withMapping() {
        {
            final var results =
                    service.getAllById(Set.of(DATA.getStationId2(), DATA.getStationId3()), StationEntity::getId);

            assertEquals(2, results.size());
            assertTrue(results.stream().anyMatch(result -> result.equals(DATA.getStationId2())));
            assertTrue(results.stream().anyMatch(result -> result.equals(DATA.getStationId3())));

            assertTrue(service.getAllById(Set.of(FAKE_UUID), StationEntity::getId).isEmpty());

            assertTrue(service.getAllById(Set.of(), StationEntity::getId).isEmpty());
        }
        {
            final var results = service.getAllById(Set.of(FAKE_UUID, DATA.getStationId1()), StationEntity::getId);

            assertEquals(1, results.size());
            assertTrue(results.stream().anyMatch(result -> result.equals(DATA.getStationId1())));
        }
    }

    @Test
    void getAll() {
        {
            final var results = service.getAll();

            assertEquals(3, results.size());
            assertTrue(results.stream().anyMatch(result -> result.getId().equals(DATA.getStationId1().toString())));
            assertTrue(results.stream().anyMatch(result -> result.getId().equals(DATA.getStationId2().toString())));
            assertTrue(results.stream().anyMatch(result -> result.getId().equals(DATA.getStationId3().toString())));
        }
        {
            final var results = service.getAll(StationEntity::getId);

            assertEquals(3, results.size());
            assertTrue(results.stream().anyMatch(result -> result.equals(DATA.getStationId1())));
            assertTrue(results.stream().anyMatch(result -> result.equals(DATA.getStationId2())));
            assertTrue(results.stream().anyMatch(result -> result.equals(DATA.getStationId3())));
        }
    }

    @Test
    void delete() {
        final var id = helper.create();

        assertTrue(helper.existsById(id));

        assertTrue(service.delete(id));

        assertFalse(helper.existsById(id));

        assertFalse(service.delete(id));
    }

    @Getter
    @Setter
    private static final class Data {
        private UUID stationId1;
        private UUID stationId2;
        private UUID stationId3;
        private boolean inited;
    }
}
