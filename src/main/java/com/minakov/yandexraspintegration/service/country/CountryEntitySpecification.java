package com.minakov.yandexraspintegration.service.country;

import com.minakov.yandexraspintegration.controller.graphql.input.country.CountryFilter;
import com.minakov.yandexraspintegration.controller.graphql.input.filter.StringCriteria;
import com.minakov.yandexraspintegration.model.CountryEntity;
import com.minakov.yandexraspintegration.model.CountryEntity_;
import com.minakov.yandexraspintegration.service.filter.AbstractEntitySpecification;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.metamodel.SingularAttribute;
import lombok.NonNull;
import org.springframework.data.util.Pair;
import org.springframework.lang.Nullable;

public class CountryEntitySpecification extends AbstractEntitySpecification<CountryEntity, CountryFilter> {
    public CountryEntitySpecification(@Nullable CountryFilter filter) {
        super(filter);
    }

    @Override
    protected @NonNull List<Pair<StringCriteria, SingularAttribute<CountryEntity, String>>> getStringCriteria(
            @Nullable final CountryFilter filter) {
        final var criteria = new ArrayList<Pair<StringCriteria, SingularAttribute<CountryEntity, String>>>();

        if (filter != null) {
            if (filter.getTitle() != null) {
//                criteria.add(Pair.of(filter.getTitle(), CountryEntity_.yandexRaspKey));
                throw new UnsupportedOperationException();
            }
        }

        return criteria;
    }

    @Override
    protected @NonNull List<Pair<StringCriteria, SingularAttribute<CountryEntity, UUID>>> getUUIDCriteria(
            @Nullable CountryFilter filter) {
        final var criteria = new ArrayList<Pair<StringCriteria, SingularAttribute<CountryEntity, UUID>>>();

        if (filter != null) {
            if (filter.getId() != null) {
                criteria.add(Pair.of(filter.getId(), CountryEntity_.id));
            }
        }

        return criteria;
    }
}
