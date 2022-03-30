package com.minakov.yandexraspintegration.service;

import com.minakov.yandexraspintegration.controller.graphql.type.dictionary.AbstractYandexDictionary;
import com.minakov.yandexraspintegration.exception.ObjectNotFoundException;
import com.minakov.yandexraspintegration.model.IEntity;
import com.minakov.yandexraspintegration.repository.GenericRepository;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.NonNull;
import org.springframework.transaction.annotation.Transactional;

public abstract class AbstractEntityService<ID, D extends AbstractYandexDictionary<?>, E extends IEntity<ID>, X extends ObjectNotFoundException> implements IEntityService<ID, D, E> {
    protected abstract @NonNull GenericRepository<E, ID> getRepository();

    protected abstract @NonNull Function<ID, X> getNoEntityError();

    protected abstract @NonNull Function<E, D> getMapper();

    @Transactional(readOnly = true)
    @Override
    public @NonNull E getEntityByIdLocked(@NonNull final ID id) {
        return getRepository().findByIdLocked(id).orElseThrow(() -> getNoEntityError().apply(id));
    }

    @Transactional(readOnly = true)
    @Override
    public @NonNull E getEntityById(@NonNull final ID id) {
        return getRepository().findById(id).orElseThrow(() -> getNoEntityError().apply(id));
    }

    @Transactional(readOnly = true)
    @Override
    public @NonNull Optional<D> getByIdOptional(@NonNull final ID id) {
        return getRepository().findById(id).map(getMapper());
    }

    @Transactional(readOnly = true)
    @Override
    public @NonNull D getById(@NonNull final ID id) {
        return getMapper().apply(getEntityById(id));
    }

    @Transactional(readOnly = true)
    @Override
    public @NonNull <T> Optional<T> getByIdOptional(@NonNull final ID id, @NonNull final Function<E, T> mapper) {
        return getRepository().findById(id).map(mapper);
    }

    @Transactional(readOnly = true)
    @Override
    public <T> @NonNull T getById(@NonNull final ID id, @NonNull final Function<E, T> mapper) {
        return mapper.apply(getEntityById(id));
    }

    @Transactional(readOnly = true)
    @Override
    public @NonNull Boolean existsById(@NonNull final ID id) {
        return getRepository().existsById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public @NonNull List<D> getAllById(@NonNull final Iterable<ID> ids) {
        return getRepository().findAllById(ids).stream().map(getMapper()).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public @NonNull <T> List<T> getAllById(@NonNull final Iterable<ID> ids, @NonNull final Function<E, T> mapper) {
        return getRepository().findAllById(ids).stream().map(mapper).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public @NonNull List<D> getAll() {
        return getRepository().findAll().stream().map(getMapper()).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public @NonNull <T> List<T> getAll(@NonNull final Function<E, T> mapper) {
        return getRepository().findAll().stream().map(mapper).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public boolean delete(@NonNull final ID id) {
        final var exists = getRepository().existsById(id);

        if (exists) {
            getRepository().deleteById(id);
        }

        return exists;
    }
}
