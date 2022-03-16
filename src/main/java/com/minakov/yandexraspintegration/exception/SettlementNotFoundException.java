package com.minakov.yandexraspintegration.exception;

import java.io.Serial;
import org.springframework.lang.Nullable;

public class SettlementNotFoundException extends ObjectNotFoundException {
    @Serial
    private static final long serialVersionUID = 1L;

    public SettlementNotFoundException(@Nullable final String id) {
        super("Settlement not found, id={}", id);
    }
}
