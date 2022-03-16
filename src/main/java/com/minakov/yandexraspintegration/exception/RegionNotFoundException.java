package com.minakov.yandexraspintegration.exception;

import java.io.Serial;
import org.springframework.lang.Nullable;

public class RegionNotFoundException extends ObjectNotFoundException {
    @Serial
    private static final long serialVersionUID = 1L;

    public RegionNotFoundException(@Nullable final String id) {
        super("Region not found, id={}", id);
    }
}
