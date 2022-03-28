package com.minakov.yandexraspintegration.service.settlement;

import com.minakov.yandexraspintegration.controller.graphql.input.filter.ICriteria;
import com.minakov.yandexraspintegration.controller.graphql.input.settlement.SettlementFilter;
import com.minakov.yandexraspintegration.model.SettlementEntity;
import com.minakov.yandexraspintegration.model.SettlementEntity_;
import com.minakov.yandexraspintegration.service.AbstractYandexRaspEntitySpecification;
import com.minakov.yandexraspintegration.service.region.RegionEntitySpecification;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.metamodel.Attribute;
import lombok.NonNull;
import org.springframework.data.util.Pair;
import org.springframework.lang.Nullable;

public class SettlementEntitySpecification extends AbstractYandexRaspEntitySpecification<SettlementEntity, SettlementFilter> {
    public SettlementEntitySpecification(@Nullable SettlementFilter filter) {
        super(filter);
    }

    @Override
    public @NonNull List<Pair<? super ICriteria, LinkedList<Attribute<?, ?>>>> getCriteria(
            @Nullable final SettlementFilter filter) {
        final var criteria = super.getCriteria(filter);

        if (filter != null) {
            if (filter.getRegion() != null) {
                final var specification = new RegionEntitySpecification(filter.getRegion());
                final var regionCriteria = specification.getCriteria(filter.getRegion());

                regionCriteria.forEach(c -> c.getSecond().addFirst(SettlementEntity_.region));

                criteria.addAll(regionCriteria);
            }
        }

        return criteria;
    }
}
