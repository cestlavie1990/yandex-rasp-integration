package com.minakov.yandexraspintegration.it.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.minakov.yandexraspintegration.exception.CountryNotFoundException;
import com.minakov.yandexraspintegration.it.AbstractIT;
import com.minakov.yandexraspintegration.it.SpringBootIT;
import com.minakov.yandexraspintegration.it.helper.CountryTestHelper;
import com.minakov.yandexraspintegration.model.CountryEntity;
import com.minakov.yandexraspintegration.service.country.CountryService;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootIT
class CountryServiceTest extends AbstractIT {
    @Autowired
    private CountryService service;
    @Autowired
    private CountryTestHelper helper;

    private static final Data DATA = new Data();

    @BeforeEach
    void beforeEach() {
        if (!DATA.isInited()) {
            DATA.setCountryId1(helper.create());
            DATA.setCountryId2(helper.create());
            DATA.setCountryId3(helper.create());
            DATA.setInited(true);
        }
    }

    @Test
    void getEntityByIdLocked() {
        final var result = service.getEntityByIdLocked(DATA.getCountryId1());

        assertEquals(DATA.getCountryId1(), result.getId());
        assertThrows(CountryNotFoundException.class, () -> service.getEntityByIdLocked(FAKE_UUID));
    }

    @Test
    void getEntityById() {
        final var result = service.getEntityById(DATA.getCountryId1());

        assertEquals(DATA.getCountryId1(), result.getId());
        assertThrows(CountryNotFoundException.class, () -> service.getEntityById(FAKE_UUID));
    }

    @Test
    void getByIdOptional() {
        {
            final var result = service.getByIdOptional(DATA.getCountryId1());

            assertTrue(result.isPresent());
            assertEquals(DATA.getCountryId1().toString(), result.get().getId());

            assertTrue(service.getByIdOptional(FAKE_UUID).isEmpty());
        }

        {
            final var result = service.getByIdOptional(DATA.getCountryId1(), CountryEntity::getId);

            assertTrue(result.isPresent());
            assertEquals(DATA.getCountryId1(), result.get());

            assertTrue(service.getByIdOptional(FAKE_UUID, CountryEntity::getId).isEmpty());
        }
    }

    @Test
    void getById() {
        {
            final var result = service.getById(DATA.getCountryId1());

            assertEquals(DATA.getCountryId1().toString(), result.getId());
            assertThrows(CountryNotFoundException.class, () -> service.getById(FAKE_UUID));
        }
        {
            final var result = service.getById(DATA.getCountryId1(), CountryEntity::getId);

            assertEquals(DATA.getCountryId1(), result);
            assertThrows(CountryNotFoundException.class, () -> service.getById(FAKE_UUID, CountryEntity::getId));
        }
    }

    @Test
    void existsById() {
        assertFalse(service.existsById(FAKE_UUID));
        assertTrue(service.existsById(DATA.getCountryId1()));
    }

    @Test
    void getAllById() {
        {
            final var results = service.getAllById(Set.of(DATA.getCountryId2(), DATA.getCountryId3()));

            assertEquals(2, results.size());
            assertTrue(results.stream().anyMatch(result -> result.getId().equals(DATA.getCountryId2().toString())));
            assertTrue(results.stream().anyMatch(result -> result.getId().equals(DATA.getCountryId3().toString())));

            assertTrue(service.getAllById(Set.of(FAKE_UUID)).isEmpty());

            assertTrue(service.getAllById(Set.of()).isEmpty());
        }
        {
            final var results = service.getAllById(Set.of(FAKE_UUID, DATA.getCountryId1()));

            assertEquals(1, results.size());
            assertTrue(results.stream().anyMatch(result -> result.getId().equals(DATA.getCountryId1().toString())));
        }
    }

    @Test
    void getAllById_withMapping() {
        {
            final var results =
                    service.getAllById(Set.of(DATA.getCountryId2(), DATA.getCountryId3()), CountryEntity::getId);

            assertEquals(2, results.size());
            assertTrue(results.stream().anyMatch(result -> result.equals(DATA.getCountryId2())));
            assertTrue(results.stream().anyMatch(result -> result.equals(DATA.getCountryId3())));

            assertTrue(service.getAllById(Set.of(FAKE_UUID), CountryEntity::getId).isEmpty());

            assertTrue(service.getAllById(Set.of(), CountryEntity::getId).isEmpty());
        }
        {
            final var results = service.getAllById(Set.of(FAKE_UUID, DATA.getCountryId1()), CountryEntity::getId);

            assertEquals(1, results.size());
            assertTrue(results.stream().anyMatch(result -> result.equals(DATA.getCountryId1())));
        }
    }

    @Test
    void getAll() {
        {
            final var results = service.getAll();

            assertTrue(results.stream().anyMatch(result -> result.getId().equals(DATA.getCountryId1().toString())));
            assertTrue(results.stream().anyMatch(result -> result.getId().equals(DATA.getCountryId2().toString())));
            assertTrue(results.stream().anyMatch(result -> result.getId().equals(DATA.getCountryId3().toString())));
        }
        {
            final var results = service.getAll(CountryEntity::getId);

            assertTrue(results.stream().anyMatch(result -> result.equals(DATA.getCountryId1())));
            assertTrue(results.stream().anyMatch(result -> result.equals(DATA.getCountryId2())));
            assertTrue(results.stream().anyMatch(result -> result.equals(DATA.getCountryId3())));
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
        private UUID countryId1;
        private UUID countryId2;
        private UUID countryId3;
        private boolean inited;
    }
}
