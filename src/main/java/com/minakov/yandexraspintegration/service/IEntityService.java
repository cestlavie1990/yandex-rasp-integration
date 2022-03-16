package com.minakov.yandexraspintegration.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import lombok.NonNull;

public interface IEntityService<ID, D, E> {
    @NonNull E getEntityByIdLocked(@NonNull final ID id);

    @NonNull E getEntityById(@NonNull final ID id);

    @NonNull Optional<D> getByIdOptional(@NonNull final ID id);

    @NonNull <T> Optional<T> getByIdOptional(@NonNull final ID id, @NonNull final Function<E, T> mapper);

    @NonNull D getById(@NonNull final ID id);

    @NonNull <T> T getById(@NonNull final ID id, @NonNull final Function<E, T> mapper);

    @NonNull Boolean existsById(@NonNull final ID id);

    @NonNull List<D> getAllById(@NonNull final Iterable<ID> ids);

    @NonNull <T> List<T> getAllById(@NonNull final Iterable<ID> ids, @NonNull final Function<E, T> mapper);

    @NonNull List<D> getAll();

    @NonNull <T> List<T> getAll(@NonNull final Function<E, T> mapper);

    boolean delete(@NonNull final ID id);
}
