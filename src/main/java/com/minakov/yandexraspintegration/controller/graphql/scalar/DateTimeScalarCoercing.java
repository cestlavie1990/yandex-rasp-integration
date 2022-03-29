package com.minakov.yandexraspintegration.controller.graphql.scalar;

import java.time.OffsetDateTime;
import lombok.NonNull;

public class DateTimeScalarCoercing extends AbstractCoercing<OffsetDateTime> {
    public DateTimeScalarCoercing() {
        super("DateTime", OffsetDateTime.class);
    }

    @Override
    public @NonNull OffsetDateTime parseString(@NonNull String value) {
        return OffsetDateTime.parse(value);
    }

    @Override
    public @NonNull String serializeToString(@NonNull OffsetDateTime value) {
        return value.toString();
    }
}
