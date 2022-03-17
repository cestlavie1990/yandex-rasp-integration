package com.minakov.yandexraspintegration.it.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.minakov.yandexraspintegration.exception.SettlementNotFoundException;
import com.minakov.yandexraspintegration.it.AbstractIT;
import com.minakov.yandexraspintegration.it.SpringBootIT;
import com.minakov.yandexraspintegration.it.helper.SettlementTestHelper;
import com.minakov.yandexraspintegration.model.SettlementEntity;
import com.minakov.yandexraspintegration.service.settlement.SettlementService;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootIT
class SettlementServiceTest extends AbstractIT {
    @Autowired
    private SettlementService service;
    @Autowired
    private SettlementTestHelper helper;

    private static final Data DATA = new Data();

    @BeforeEach
    void beforeEach() {
        if (!DATA.isInited()) {
            DATA.setSettlementId1(helper.create());
            DATA.setSettlementId2(helper.create());
            DATA.setSettlementId3(helper.create());
            DATA.setInited(true);
        }
    }

    @Test
    void getEntityByIdLocked() {
        final var result = service.getEntityByIdLocked(DATA.getSettlementId1());

        assertEquals(DATA.getSettlementId1(), result.getId());
        assertThrows(SettlementNotFoundException.class, () -> service.getEntityByIdLocked(FAKE_UUID));
    }

    @Test
    void getEntityById() {
        final var result = service.getEntityById(DATA.getSettlementId1());

        assertEquals(DATA.getSettlementId1(), result.getId());
        assertThrows(SettlementNotFoundException.class, () -> service.getEntityById(FAKE_UUID));
    }

    @Test
    void getByIdOptional() {
        {
            final var result = service.getByIdOptional(DATA.getSettlementId1());

            assertTrue(result.isPresent());
            assertEquals(DATA.getSettlementId1().toString(), result.get().getId());

            assertTrue(service.getByIdOptional(FAKE_UUID).isEmpty());
        }

        {
            final var result = service.getByIdOptional(DATA.getSettlementId1(), SettlementEntity::getId);

            assertTrue(result.isPresent());
            assertEquals(DATA.getSettlementId1(), result.get());

            assertTrue(service.getByIdOptional(FAKE_UUID, SettlementEntity::getId).isEmpty());
        }
    }

    @Test
    void getById() {
        {
            final var result = service.getById(DATA.getSettlementId1());

            assertEquals(DATA.getSettlementId1().toString(), result.getId());
            assertThrows(SettlementNotFoundException.class, () -> service.getById(FAKE_UUID));
        }
        {
            final var result = service.getById(DATA.getSettlementId1(), SettlementEntity::getId);

            assertEquals(DATA.getSettlementId1(), result);
            assertThrows(SettlementNotFoundException.class, () -> service.getById(FAKE_UUID, SettlementEntity::getId));
        }
    }

    @Test
    void existsById() {
        assertFalse(service.existsById(FAKE_UUID));
        assertTrue(service.existsById(DATA.getSettlementId1()));
    }

    @Test
    void getAllById() {
        {
            final var results = service.getAllById(Set.of(DATA.getSettlementId2(), DATA.getSettlementId3()));

            assertEquals(2, results.size());
            assertTrue(results.stream().anyMatch(result -> result.getId().equals(DATA.getSettlementId2().toString())));
            assertTrue(results.stream().anyMatch(result -> result.getId().equals(DATA.getSettlementId3().toString())));

            assertTrue(service.getAllById(Set.of(FAKE_UUID)).isEmpty());

            assertTrue(service.getAllById(Set.of()).isEmpty());
        }
        {
            final var results = service.getAllById(Set.of(FAKE_UUID, DATA.getSettlementId1()));

            assertEquals(1, results.size());
            assertTrue(results.stream().anyMatch(result -> result.getId().equals(DATA.getSettlementId1().toString())));
        }
    }

    @Test
    void getAllById_withMapping() {
        {
            final var results = service.getAllById(Set.of(DATA.getSettlementId2(), DATA.getSettlementId3()),
                    SettlementEntity::getId);

            assertEquals(2, results.size());
            assertTrue(results.stream().anyMatch(result -> result.equals(DATA.getSettlementId2())));
            assertTrue(results.stream().anyMatch(result -> result.equals(DATA.getSettlementId3())));

            assertTrue(service.getAllById(Set.of(FAKE_UUID), SettlementEntity::getId).isEmpty());

            assertTrue(service.getAllById(Set.of(), SettlementEntity::getId).isEmpty());
        }
        {
            final var results = service.getAllById(Set.of(FAKE_UUID, DATA.getSettlementId1()), SettlementEntity::getId);

            assertEquals(1, results.size());
            assertTrue(results.stream().anyMatch(result -> result.equals(DATA.getSettlementId1())));
        }
    }

    @Test
    void getAll() {
        {
            final var results = service.getAll();

            assertTrue(results.stream().anyMatch(result -> result.getId().equals(DATA.getSettlementId1().toString())));
            assertTrue(results.stream().anyMatch(result -> result.getId().equals(DATA.getSettlementId2().toString())));
            assertTrue(results.stream().anyMatch(result -> result.getId().equals(DATA.getSettlementId3().toString())));
        }
        {
            final var results = service.getAll(SettlementEntity::getId);

            assertTrue(results.stream().anyMatch(result -> result.equals(DATA.getSettlementId1())));
            assertTrue(results.stream().anyMatch(result -> result.equals(DATA.getSettlementId2())));
            assertTrue(results.stream().anyMatch(result -> result.equals(DATA.getSettlementId3())));
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
        private UUID settlementId1;
        private UUID settlementId2;
        private UUID settlementId3;
        private boolean inited;
    }
}
