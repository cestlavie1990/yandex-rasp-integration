package com.minakov.yandexraspintegration.service.country;

import com.minakov.yandexraspintegration.controller.graphql.input.country.CountryFilter;
import com.minakov.yandexraspintegration.controller.graphql.input.filter.ICriteria;
import com.minakov.yandexraspintegration.model.CountryEntity;
import com.minakov.yandexraspintegration.model.CountryEntity_;
import com.minakov.yandexraspintegration.service.filter.AbstractEntitySpecification;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.metamodel.SingularAttribute;
import lombok.NonNull;
import org.springframework.data.util.Pair;
import org.springframework.lang.Nullable;

public class CountryEntitySpecification extends AbstractEntitySpecification<CountryEntity, CountryFilter> {
    public CountryEntitySpecification(@Nullable CountryFilter filter) {
        super(filter);
    }

    @Override
    protected @NonNull List<Pair<? super ICriteria, SingularAttribute<CountryEntity, ?>>> getCriteria(
            @Nullable final CountryFilter filter) {
        final var criteria = new ArrayList<Pair<? super ICriteria, SingularAttribute<CountryEntity, ?>>>();

        if (filter != null) {
            if (filter.getId() != null) {
                criteria.add(Pair.of(filter.getId(), CountryEntity_.id));
            }
            if (filter.getTitle() != null) {
                criteria.add(Pair.of(filter.getTitle(), CountryEntity_.title));
            }
        }

        return criteria;
    }
}
