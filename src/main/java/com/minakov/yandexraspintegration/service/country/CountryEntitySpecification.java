package com.minakov.yandexraspintegration.service.country;

import com.minakov.yandexraspintegration.controller.graphql.input.country.CountryFilter;
import com.minakov.yandexraspintegration.controller.graphql.input.filter.ICriteria;
import com.minakov.yandexraspintegration.model.CountryEntity;
import com.minakov.yandexraspintegration.service.AbstractYandexStationEntitySpecification;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.metamodel.Attribute;
import lombok.NonNull;
import org.springframework.data.util.Pair;
import org.springframework.lang.Nullable;

public class CountryEntitySpecification extends AbstractYandexStationEntitySpecification<CountryEntity, CountryFilter> {
    public CountryEntitySpecification(@Nullable CountryFilter filter) {
        super(filter);
    }

    @Override
    public @NonNull List<Pair<? super ICriteria, LinkedList<Attribute<?, ?>>>> getCriteria(
            @Nullable CountryFilter filter) {
        return super.getCriteria(filter);
    }
}
