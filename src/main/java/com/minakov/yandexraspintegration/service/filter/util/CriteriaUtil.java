package com.minakov.yandexraspintegration.service.filter.util;

import com.minakov.yandexraspintegration.controller.graphql.input.filter.StringCriteria;
import com.minakov.yandexraspintegration.controller.graphql.input.filter.UUIDCriteria;
import java.util.UUID;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.springframework.lang.Nullable;

@UtilityClass
public class CriteriaUtil {
    @Nullable
    public static Predicate getStringPredicate(@NonNull final StringCriteria criteria, @NonNull final Path<String> path,
            @NonNull final CriteriaBuilder builder) {
        Predicate predicate = null;

        if (criteria.getIn() != null) {
            predicate = CriteriaValueUtil.getIn(path, criteria.getIn().getValues(), criteria.getIn().getInverse());
        }
        if (criteria.getLike() != null) {
            final var like = CriteriaValueUtil.getLike(builder, path, criteria.getLike().getValues(),
                    criteria.getLike().getInverse());
            predicate = predicate == null ? like : builder.and(predicate, like);
        }
        return predicate;
    }

    @Nullable
    public static Predicate getUUIDPredicate(@NonNull final UUIDCriteria criteria, @NonNull final Path<UUID> path) {
        Predicate predicate = null;

        if (criteria.getIn() != null) {
            predicate = CriteriaValueUtil.getIn(path, criteria.getIn().getValues(), criteria.getIn().getInverse());
        }
        return predicate;
    }
}
