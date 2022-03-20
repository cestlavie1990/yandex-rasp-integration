package com.minakov.yandexraspintegration.service.filter.util;

import com.minakov.yandexraspintegration.controller.graphql.input.filter.StringCriteria;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.springframework.lang.Nullable;

@UtilityClass
public class CriteriaUtil {
    @Nullable
    public static <E> Predicate getStringPredicate(@NonNull final StringCriteria criteria, @NonNull final Root<E> root,
            @NonNull final SingularAttribute<E, String> attr, @NonNull final CriteriaBuilder builder) {
        final var path = root.get(attr);

        Predicate predicate = null;

        if (criteria.getIn() != null) {
            predicate = CriteriaValueUtil.getIn(path, criteria.getIn().getValues(), criteria.getIn().getInverse());
        }
        if (criteria.getLike() != null) {
            predicate = builder.and(predicate, CriteriaValueUtil.getLike(builder, path, criteria.getLike().getValues(),
                    criteria.getLike().getInverse()));
        }
        return predicate;
    }

    @Nullable
    public static <E> Predicate getUUIDPredicate(@NonNull final StringCriteria criteria, @NonNull final Root<E> root,
            @NonNull final SingularAttribute<E, UUID> attr, @NonNull final CriteriaBuilder builder) {
        final var path = root.get(attr);

        Predicate predicate = null;

        if (criteria.getIn() != null) {
            predicate = CriteriaValueUtil.getIn(path,
                    criteria.getIn().getValues().stream().map(UUID::fromString).collect(Collectors.toList()),
                    criteria.getIn().getInverse());
        }
        return predicate;
    }
}
