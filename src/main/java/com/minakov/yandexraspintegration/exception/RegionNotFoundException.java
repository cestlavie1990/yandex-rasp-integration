package com.minakov.yandexraspintegration.exception;

import java.io.Serial;
import java.util.UUID;
import org.springframework.lang.Nullable;

public class RegionNotFoundException extends ObjectNotFoundException {
    @Serial
    private static final long serialVersionUID = 1L;

    public RegionNotFoundException(@Nullable final UUID id) {
        super("Region not found, id={}", id);
    }
}
