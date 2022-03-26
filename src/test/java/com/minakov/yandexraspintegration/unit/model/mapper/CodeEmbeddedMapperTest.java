package com.minakov.yandexraspintegration.unit.model.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.minakov.yandexraspintegration.feign.dto.yandex.rasp.CodeDto;
import com.minakov.yandexraspintegration.model.mapper.CodeEmbeddedMapper;
import java.util.List;
import org.junit.jupiter.api.Test;

class CodeEmbeddedMapperTest {
    private static final CodeEmbeddedMapper MAPPER = CodeEmbeddedMapper.INSTANCE;

    @Test
    void toDto_withoutNull() {
        final var source = CodeDto.builder().esrCode("esr-code").yandexCode("yandex-code").build();

        final var target = MAPPER.map(source);

        assertEquals(source.getEsrCode(), target.getEsrCode());
        assertEquals(source.getYandexCode(), target.getYandexCode());
    }

    @Test
    void toDto_withNull() {
        final var source = CodeDto.builder().build();

        final var target = MAPPER.map(source);

        assertNull(target.getEsrCode());
        assertNull(target.getYandexCode());
    }

    @Test
    void toDto_withBlankValues() {
        final var source = CodeDto.builder().esrCode("").yandexCode(" ").build();

        final var target = MAPPER.map(source);

        assertNull(target.getEsrCode());
        assertNull(target.getYandexCode());
    }

    @Test
    void toDto_list() {
        final var source = CodeDto.builder().esrCode("").yandexCode(" ").build();

        final var target = MAPPER.map(source);
        final var targets = MAPPER.map(List.of(source));

        assertEquals(1, targets.size());
        assertTrue(targets.stream().anyMatch(result -> result.equals(target)));
    }
}
