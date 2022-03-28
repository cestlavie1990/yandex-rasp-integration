package com.minakov.yandexraspintegration.service.station;

import com.minakov.yandexraspintegration.controller.graphql.input.filter.ICriteria;
import com.minakov.yandexraspintegration.controller.graphql.input.station.StationFilter;
import com.minakov.yandexraspintegration.model.StationEntity;
import com.minakov.yandexraspintegration.model.StationEntity_;
import com.minakov.yandexraspintegration.service.AbstractYandexRaspEntitySpecification;
import com.minakov.yandexraspintegration.service.settlement.SettlementEntitySpecification;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.metamodel.Attribute;
import lombok.NonNull;
import org.springframework.data.util.Pair;
import org.springframework.lang.Nullable;

public class StationEntitySpecification extends AbstractYandexRaspEntitySpecification<StationEntity, StationFilter> {
    public StationEntitySpecification(@Nullable StationFilter filter) {
        super(filter);
    }

    @Override
    public @NonNull List<Pair<? super ICriteria, LinkedList<Attribute<?, ?>>>> getCriteria(
            @Nullable final StationFilter filter) {
        final var criteria = super.getCriteria(filter);

        if (filter != null) {
            if (filter.getSettlement() != null) {
                final var specification = new SettlementEntitySpecification(filter.getSettlement());
                final var settlementCriteria = specification.getCriteria(filter.getSettlement());

                settlementCriteria.forEach(c -> c.getSecond().addFirst(StationEntity_.settlement));

                criteria.addAll(settlementCriteria);
            }
        }

        return criteria;
    }
}
