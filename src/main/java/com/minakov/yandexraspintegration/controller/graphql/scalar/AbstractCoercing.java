package com.minakov.yandexraspintegration.controller.graphql.scalar;

import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@SuppressWarnings("unchecked")
@RequiredArgsConstructor
public abstract class AbstractCoercing<T> implements Coercing<T, String> {
    @NonNull
    private final String scalarName;
    @NonNull
    private final Class<T> javaType;

    @NonNull
    public abstract T parseString(@NonNull String value);

    @NonNull
    public abstract String serializeToString(@NonNull T value);

    @Override
    public String serialize(@NonNull Object dataFetcherResult) throws CoercingSerializeException {
        if (javaType.isAssignableFrom(dataFetcherResult.getClass())) {
            return serializeToString((T) dataFetcherResult);
        }
        if (dataFetcherResult instanceof String) {
            return serializeToString(parseString((String) dataFetcherResult));
        }
        throw new CoercingSerializeException(prepareErrorMessage(dataFetcherResult));
    }

    @NonNull
    @Override
    public T parseValue(@NonNull Object dataFetcherResult) throws CoercingParseValueException {
        if (dataFetcherResult instanceof String) {
            return parseString((String) dataFetcherResult);
        }
        throw new CoercingParseValueException(prepareErrorMessage(dataFetcherResult));
    }

    @NonNull
    @Override
    public T parseLiteral(@NonNull Object dataFetcherResult) throws CoercingParseLiteralException {
        if (!(dataFetcherResult instanceof StringValue)) {
            throw new CoercingParseLiteralException(prepareErrorMessage(dataFetcherResult));
        }

        final var value = ((StringValue) dataFetcherResult).getValue();

        return parseString(value);
    }

    @NonNull
    private String prepareErrorMessage(@NonNull final Object dataFetcherResult) {
        return String.format("Invalid value: scalar=%s, value=%s", scalarName, dataFetcherResult);
    }
}
