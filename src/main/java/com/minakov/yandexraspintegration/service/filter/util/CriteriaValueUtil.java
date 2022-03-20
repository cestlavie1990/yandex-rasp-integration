package com.minakov.yandexraspintegration.service.filter.util;

import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CriteriaValueUtil {
    @NonNull
    public static <T> Predicate getIn(@NonNull final Path<T> path, @NonNull final List<T> values, boolean inverse) {
        return inverse ? path.in(values).not() : path.in(values);
    }

    @NonNull
    public static Predicate getLike(@NonNull final CriteriaBuilder builder, @NonNull final Path<String> path,
            final List<String> values, boolean inverse) {
        final var predicate = builder.or(values.stream().map(val -> builder.like(path, val)).toArray(Predicate[]::new));

        return inverse ? predicate.not() : predicate;
    }
}
