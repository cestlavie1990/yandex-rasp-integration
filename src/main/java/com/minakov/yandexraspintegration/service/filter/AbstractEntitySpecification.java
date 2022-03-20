package com.minakov.yandexraspintegration.service.filter;

import com.minakov.yandexraspintegration.controller.graphql.input.filter.IFilter;
import com.minakov.yandexraspintegration.controller.graphql.input.filter.StringCriteria;
import com.minakov.yandexraspintegration.model.IEntity;
import com.minakov.yandexraspintegration.service.filter.util.CriteriaUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.util.Pair;
import org.springframework.lang.Nullable;

@RequiredArgsConstructor
public abstract class AbstractEntitySpecification<E extends IEntity<?>, F extends IFilter<F>> implements Specification<E> {
    @Nullable
    private final F filter;

    @NonNull
    protected List<Pair<StringCriteria, SingularAttribute<E, String>>> getStringCriteria(@Nullable final F filter) {
        return Collections.emptyList();
    }

    @NonNull
    protected List<Pair<StringCriteria, SingularAttribute<E, UUID>>> getUUIDCriteria(@Nullable final F filter) {
        return Collections.emptyList();
    }

    @Nullable
    @Override
    public Predicate toPredicate(@NonNull final Root<E> root, @NonNull final CriteriaQuery<?> query,
            @NonNull final CriteriaBuilder builder) {
        if (filter == null) {
            return null;
        }

        final var predicates = new ArrayList<Predicate>();

        addPredicates(root, builder, filter, p -> {
            if (p != null) {
                predicates.add(p);
            }
        });

        return predicates.isEmpty() ? null : builder.and(predicates.toArray(Predicate[]::new));
    }

    private void addPredicates(final Root<E> root, final CriteriaBuilder builder, final F filter,
            final Consumer<Predicate> consumer) {
        getStringCriteria(filter).forEach(
                c -> consumer.accept(CriteriaUtil.getStringPredicate((c.getFirst()), root, c.getSecond(), builder)));
        getUUIDCriteria(filter).forEach(
                c -> consumer.accept(CriteriaUtil.getUUIDPredicate((c.getFirst()), root, c.getSecond(), builder)));
    }
}
