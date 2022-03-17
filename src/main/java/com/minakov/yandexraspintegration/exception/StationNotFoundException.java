package com.minakov.yandexraspintegration.exception;

import java.io.Serial;
import java.util.UUID;
import org.springframework.lang.Nullable;

public class StationNotFoundException extends ObjectNotFoundException {
    @Serial
    private static final long serialVersionUID = 1L;

    public StationNotFoundException(@Nullable final UUID id) {
        super("Station not found, id={}", id);
    }
}
