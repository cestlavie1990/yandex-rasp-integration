package com.minakov.yandexraspintegration.service.filter;

import com.minakov.yandexraspintegration.controller.graphql.input.filter.BaseFilter;
import com.minakov.yandexraspintegration.controller.graphql.input.filter.ICriteria;
import com.minakov.yandexraspintegration.controller.graphql.input.filter.StringCriteria;
import com.minakov.yandexraspintegration.model.IEntity;
import com.minakov.yandexraspintegration.service.filter.util.PredicateUtil;
import java.util.ArrayList;
import java.util.List;
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
public abstract class AbstractEntitySpecification<E extends IEntity<?>, F extends BaseFilter<F>> implements Specification<E> {
    @Nullable
    private final F filter;

    @NonNull
    protected abstract List<Pair<? super ICriteria, SingularAttribute<E, ?>>> getCriteria(@Nullable final F filter);

    @Override
    public @NonNull Predicate toPredicate(@NonNull final Root<E> root, @NonNull final CriteriaQuery<?> query,
            @NonNull final CriteriaBuilder builder) {
        if (filter == null) {
            return builder.conjunction();
        }

        final var predicates = new ArrayList<Predicate>();

        addPredicates(root, builder, filter, predicates::add);

        if (filter.getAnd() != null) {
            filter.getAnd().forEach(f -> addPredicates(root, builder, f, predicates::add));
        }
        if (filter.getOr() != null) {
            filter.getOr().forEach(f -> addPredicates(root, builder, f, predicates::add));
        }

        return predicates.isEmpty() ? builder.conjunction() : builder.and(predicates.toArray(Predicate[]::new));
    }

    @SuppressWarnings("unchecked")
    private void addPredicates(final Root<E> root, final CriteriaBuilder builder, final F filter,
            final Consumer<Predicate> consumer) {
        final var criteria = getCriteria(filter);

        criteria.forEach(c -> {
            if (c.getFirst() instanceof StringCriteria) {
                consumer.accept(PredicateUtil.getPredicate(((StringCriteria) c.getFirst()), root,
                        (SingularAttribute<E, String>) c.getSecond(), builder));
            }
        });
    }
}
