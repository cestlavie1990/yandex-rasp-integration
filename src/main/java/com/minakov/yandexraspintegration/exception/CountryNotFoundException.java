package com.minakov.yandexraspintegration.exception;

import java.io.Serial;
import org.springframework.lang.Nullable;

public class CountryNotFoundException extends ObjectNotFoundException {
    @Serial
    private static final long serialVersionUID = 1L;

    public CountryNotFoundException(@Nullable final String id) {
        super("Country not found, id={}", id);
    }
}
