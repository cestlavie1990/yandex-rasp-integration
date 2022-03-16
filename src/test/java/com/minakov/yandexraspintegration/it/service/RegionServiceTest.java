package com.minakov.yandexraspintegration.it.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.minakov.yandexraspintegration.exception.RegionNotFoundException;
import com.minakov.yandexraspintegration.it.AbstractIT;
import com.minakov.yandexraspintegration.it.SpringBootIT;
import com.minakov.yandexraspintegration.it.helper.RegionTestHelper;
import com.minakov.yandexraspintegration.model.RegionEntity;
import com.minakov.yandexraspintegration.service.region.RegionService;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootIT
class RegionServiceTest extends AbstractIT {
    @Autowired
    private RegionService service;
    @Autowired
    private RegionTestHelper helper;

    private static final Data DATA = new Data();

    @BeforeEach
    void beforeEach() {
        if (!DATA.isInited()) {
            DATA.setRegionId1(helper.create());
            DATA.setRegionId2(helper.create());
            DATA.setRegionId3(helper.create());
            DATA.setInited(true);
        }
    }

    @Test
    void getEntityByIdLocked() {
        final var result = service.getEntityByIdLocked(DATA.getRegionId1());

        assertEquals(DATA.getRegionId1(), result.getId());
        assertThrows(RegionNotFoundException.class, () -> service.getEntityByIdLocked(FAKE_UUID));
    }

    @Test
    void getEntityById() {
        final var result = service.getEntityById(DATA.getRegionId1());

        assertEquals(DATA.getRegionId1(), result.getId());
        assertThrows(RegionNotFoundException.class, () -> service.getEntityById(FAKE_UUID));
    }

    @Test
    void getByIdOptional() {
        {
            final var result = service.getByIdOptional(DATA.getRegionId1());

            assertTrue(result.isPresent());
            assertEquals(DATA.getRegionId1().toString(), result.get().getId());

            assertTrue(service.getByIdOptional(FAKE_UUID).isEmpty());
        }

        {
            final var result = service.getByIdOptional(DATA.getRegionId1(), RegionEntity::getId);

            assertTrue(result.isPresent());
            assertEquals(DATA.getRegionId1(), result.get());

            assertTrue(service.getByIdOptional(FAKE_UUID, RegionEntity::getId).isEmpty());
        }
    }

    @Test
    void getById() {
        {
            final var result = service.getById(DATA.getRegionId1());

            assertEquals(DATA.getRegionId1().toString(), result.getId());
            assertThrows(RegionNotFoundException.class, () -> service.getById(FAKE_UUID));
        }
        {
            final var result = service.getById(DATA.getRegionId1(), RegionEntity::getId);

            assertEquals(DATA.getRegionId1(), result);
            assertThrows(RegionNotFoundException.class, () -> service.getById(FAKE_UUID, RegionEntity::getId));
        }
    }

    @Test
    void existsById() {
        assertFalse(service.existsById(FAKE_UUID));
        assertTrue(service.existsById(DATA.getRegionId1()));
    }

    @Test
    void getAllById() {
        {
            final var results = service.getAllById(Set.of(DATA.getRegionId2(), DATA.getRegionId3()));

            assertEquals(2, results.size());
            assertTrue(results.stream().anyMatch(result -> result.getId().equals(DATA.getRegionId2().toString())));
            assertTrue(results.stream().anyMatch(result -> result.getId().equals(DATA.getRegionId3().toString())));

            assertTrue(service.getAllById(Set.of(FAKE_UUID)).isEmpty());

            assertTrue(service.getAllById(Set.of()).isEmpty());
        }
        {
            final var results = service.getAllById(Set.of(FAKE_UUID, DATA.getRegionId1()));

            assertEquals(1, results.size());
            assertTrue(results.stream().anyMatch(result -> result.getId().equals(DATA.getRegionId1().toString())));
        }
    }

    @Test
    void getAllById_withMapping() {
        {
            final var results =
                    service.getAllById(Set.of(DATA.getRegionId2(), DATA.getRegionId3()), RegionEntity::getId);

            assertEquals(2, results.size());
            assertTrue(results.stream().anyMatch(result -> result.equals(DATA.getRegionId2())));
            assertTrue(results.stream().anyMatch(result -> result.equals(DATA.getRegionId3())));

            assertTrue(service.getAllById(Set.of(FAKE_UUID), RegionEntity::getId).isEmpty());

            assertTrue(service.getAllById(Set.of(), RegionEntity::getId).isEmpty());
        }
        {
            final var results = service.getAllById(Set.of(FAKE_UUID, DATA.getRegionId1()), RegionEntity::getId);

            assertEquals(1, results.size());
            assertTrue(results.stream().anyMatch(result -> result.equals(DATA.getRegionId1())));
        }
    }

    @Test
    void getAll() {
        {
            final var results = service.getAll();

            assertEquals(3, results.size());
            assertTrue(results.stream().anyMatch(result -> result.getId().equals(DATA.getRegionId1().toString())));
            assertTrue(results.stream().anyMatch(result -> result.getId().equals(DATA.getRegionId2().toString())));
            assertTrue(results.stream().anyMatch(result -> result.getId().equals(DATA.getRegionId3().toString())));
        }
        {
            final var results = service.getAll(RegionEntity::getId);

            assertEquals(3, results.size());
            assertTrue(results.stream().anyMatch(result -> result.equals(DATA.getRegionId1())));
            assertTrue(results.stream().anyMatch(result -> result.equals(DATA.getRegionId2())));
            assertTrue(results.stream().anyMatch(result -> result.equals(DATA.getRegionId3())));
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
        private UUID regionId1;
        private UUID regionId2;
        private UUID regionId3;
        private boolean inited;
    }
}
