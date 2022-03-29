package com.minakov.yandexraspintegration.controller.graphql.scalar;

import java.time.LocalDate;
import lombok.NonNull;

public class DateScalarCoercing extends AbstractCoercing<LocalDate> {
    public DateScalarCoercing() {
        super("Date", LocalDate.class);
    }

    @Override
    public @NonNull LocalDate parseString(@NonNull String value) {
        return LocalDate.parse(value);
    }

    @Override
    public @NonNull String serializeToString(@NonNull LocalDate value) {
        return value.toString();
    }
}
