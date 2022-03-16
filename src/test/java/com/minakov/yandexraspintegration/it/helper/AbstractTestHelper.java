package com.minakov.yandexraspintegration.it.helper;

import com.minakov.yandexraspintegration.model.IEntity;
import com.minakov.yandexraspintegration.repository.GenericRepository;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.annotation.Nullable;
import lombok.NonNull;
import org.springframework.transaction.annotation.Transactional;

public abstract class AbstractTestHelper<ID, E extends IEntity<ID>> {
    @Transactional(readOnly = true)
    public @Nullable
    <T> T get(@NonNull final ID id, @NonNull final Function<E, T> mapper) {
        return mapper.apply(getRepository().findById(id).orElseThrow());
    }

    @Transactional(readOnly = true)
    public boolean existsById(@NonNull final ID id) {
        return getRepository().existsById(id);
    }

    @Transactional
    public void update(@NonNull final ID id, @NonNull final Consumer<E> consumer) {
        final var entity = Objects.requireNonNull(get(id, Function.identity()));

        consumer.accept(entity);

        getRepository().save(entity);
    }

    public abstract @NonNull ID create();

    protected abstract @NonNull GenericRepository<E, ID> getRepository();
}
