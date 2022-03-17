package com.minakov.yandexraspintegration.exception;

import java.io.Serial;
import java.util.UUID;
import org.springframework.lang.Nullable;

public class CountryNotFoundException extends ObjectNotFoundException {
    @Serial
    private static final long serialVersionUID = 1L;

    public CountryNotFoundException(@Nullable final UUID id) {
        super("Country not found, id={}", id);
    }
}
