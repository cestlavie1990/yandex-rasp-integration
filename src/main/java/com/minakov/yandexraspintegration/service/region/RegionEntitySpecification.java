package com.minakov.yandexraspintegration.service.region;

import com.minakov.yandexraspintegration.controller.graphql.input.filter.ICriteria;
import com.minakov.yandexraspintegration.controller.graphql.input.region.RegionFilter;
import com.minakov.yandexraspintegration.model.RegionEntity;
import com.minakov.yandexraspintegration.model.RegionEntity_;
import com.minakov.yandexraspintegration.service.country.CountryEntitySpecification;
import com.minakov.yandexraspintegration.service.filter.AbstractEntitySpecification;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.metamodel.Attribute;
import lombok.NonNull;
import org.springframework.data.util.Pair;
import org.springframework.lang.Nullable;

public class RegionEntitySpecification extends AbstractEntitySpecification<RegionEntity, RegionFilter> {
    public RegionEntitySpecification(@Nullable RegionFilter filter) {
        super(filter);
    }

    @Override
    public @NonNull List<Pair<? super ICriteria, LinkedList<Attribute<?, ?>>>> getCriteria(
            @Nullable RegionFilter filter) {
        final var criteria = super.getCriteria(filter);

        if (filter != null) {
            if (filter.getCountry() != null) {
                final var specification = new CountryEntitySpecification(filter.getCountry());
                final var countryCriteria = specification.getCriteria(filter.getCountry());

                countryCriteria.forEach(c -> c.getSecond().addFirst(RegionEntity_.country));

                criteria.addAll(countryCriteria);
            }
        }

        return criteria;
    }
}
