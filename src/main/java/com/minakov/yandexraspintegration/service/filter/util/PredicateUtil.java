package com.minakov.yandexraspintegration.service.filter.util;

import com.minakov.yandexraspintegration.controller.graphql.input.filter.StringCriteria;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PredicateUtil {
    @NonNull
    public static <E> Predicate getPredicate(@NonNull final StringCriteria criteria, @NonNull final Root<E> root,
            @NonNull final SingularAttribute<E, String> attr, @NonNull final CriteriaBuilder builder) {
        Predicate predicate = builder.conjunction();

        final var path = root.get(attr);

        if (criteria.getIn() != null) {
            predicate =
                    builder.and(predicate, getIn(path, criteria.getIn().getValues(), criteria.getIn().getInverse()));
        }
        if (criteria.getLike() != null) {
            predicate = builder.and(predicate,
                    getLike(builder, path, criteria.getLike().getValues(), criteria.getLike().getInverse()));
        }
        return predicate;
    }

    private static Predicate getIn(final Path<String> path, final List<String> values, boolean inverse) {
        return inverse ? path.in(values).not() : path.in(values);
    }

    private static Predicate getLike(final CriteriaBuilder builder, final Path<String> path, final List<String> values,
            boolean inverse) {
        final var predicate =
                builder.and(values.stream().map(val -> builder.like(path, val)).toArray(Predicate[]::new));

        return inverse ? predicate.not() : predicate;
    }
}
