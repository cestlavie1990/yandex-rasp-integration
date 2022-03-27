package com.minakov.yandexraspintegration.service.filter;

import com.minakov.yandexraspintegration.controller.graphql.input.filter.ICriteria;
import com.minakov.yandexraspintegration.controller.graphql.input.filter.IFilter;
import com.minakov.yandexraspintegration.controller.graphql.input.filter.StringCriteria;
import com.minakov.yandexraspintegration.controller.graphql.input.filter.UUIDCriteria;
import com.minakov.yandexraspintegration.model.IEntity;
import com.minakov.yandexraspintegration.service.filter.util.CriteriaUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Attribute;
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
    protected List<Pair<? super ICriteria, List<Attribute<?, ?>>>> getCriteria(@Nullable final F filter) {
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

    @SuppressWarnings("unchecked")
    private void addPredicates(final Root<E> root, final CriteriaBuilder builder, final F filter,
            final Consumer<Predicate> consumer) {
        getCriteria(filter).forEach(c -> {
            if (c.getFirst() instanceof StringCriteria) {
                consumer.accept(CriteriaUtil.getStringPredicate(((StringCriteria) c.getFirst()),
                        (Path<String>) getAttribute(root, c.getSecond()), builder));
            } else if (c.getFirst() instanceof UUIDCriteria) {
                consumer.accept(CriteriaUtil.getUUIDPredicate(((UUIDCriteria) c.getFirst()),
                        (Path<UUID>) getAttribute(root, c.getSecond())));
            }
        });
    }

    private Path<?> getAttribute(final Root<E> root, final List<Attribute<?, ?>> paths) {
        Path<?> result = null;
        for (Attribute<?, ?> path : paths) {
            result = result == null ? root.get(path.getName()) : result.get(path.getName());
        }
        return result;
    }
}
