package com.minakov.yandexraspintegration.repository;

import java.util.List;
import java.util.Optional;
import javax.persistence.LockModeType;
import lombok.NonNull;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

@NoRepositoryBean
public interface GenericRepository<E, ID> extends Repository<E, ID> {
    @NonNull E save(@NonNull E model);

    @NonNull List<E> saveAll(@NonNull Iterable<E> entities);

    @NonNull Optional<E> findById(@NonNull ID id);

    @Query("FROM #{#entityName} e WHERE e.id = :id")
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @NonNull Optional<E> findByIdLocked(ID id);

    @NonNull List<E> findAllById(@NonNull Iterable<ID> ids);

    @NonNull List<E> findAll();

    @NonNull Boolean existsById(@NonNull ID id);

    void deleteById(@NonNull ID id);

    void deleteAll();
}
