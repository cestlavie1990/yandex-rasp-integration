package com.minakov.yandexraspintegration.service.country;

import com.minakov.yandexraspintegration.controller.graphql.input.country.CountryFilter;
import com.minakov.yandexraspintegration.controller.graphql.input.filter.ICriteria;
import com.minakov.yandexraspintegration.model.CountryEntity;
import com.minakov.yandexraspintegration.model.CountryEntity_;
import com.minakov.yandexraspintegration.model.embedded.CodeEmbedded_;
import com.minakov.yandexraspintegration.service.filter.AbstractEntitySpecification;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.metamodel.Attribute;
import lombok.NonNull;
import org.springframework.data.util.Pair;
import org.springframework.lang.Nullable;

public class CountryEntitySpecification extends AbstractEntitySpecification<CountryEntity, CountryFilter> {
    public CountryEntitySpecification(@Nullable CountryFilter filter) {
        super(filter);
    }

    @Override
    protected @NonNull List<Pair<? super ICriteria, List<Attribute<?, ?>>>> getCriteria(
            @Nullable CountryFilter filter) {
        final var criteria = new ArrayList<Pair<? super ICriteria, List<Attribute<?, ?>>>>();

        if (filter != null) {
            if (filter.getId() != null) {
                criteria.add(Pair.of(filter.getId(), List.of(CountryEntity_.id)));
            }
            if (filter.getTitle() != null) {
                criteria.add(Pair.of(filter.getTitle(), List.of(CountryEntity_.title)));
            }
            if (filter.getCode() != null) {
                final var code = filter.getCode();
                if (code.getEsrCode() != null) {
                    criteria.add(Pair.of(code.getEsrCode(), List.of(CountryEntity_.code, CodeEmbedded_.esrCode)));
                }
                if (code.getYandexCode() != null) {
                    criteria.add(Pair.of(code.getYandexCode(), List.of(CountryEntity_.code, CodeEmbedded_.yandexCode)));
                }
            }
        }

        return criteria;
    }
}
