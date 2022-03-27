package com.minakov.yandexraspintegration.service;

import com.minakov.yandexraspintegration.controller.graphql.input.filter.ICriteria;
import com.minakov.yandexraspintegration.controller.graphql.input.filter.IYandexRaspFilter;
import com.minakov.yandexraspintegration.model.CountryEntity_;
import com.minakov.yandexraspintegration.model.IYandexRaspEntity;
import com.minakov.yandexraspintegration.model.embedded.CodeEmbedded_;
import com.minakov.yandexraspintegration.service.filter.AbstractEntitySpecification;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.metamodel.Attribute;
import lombok.NonNull;
import org.springframework.data.util.Pair;
import org.springframework.lang.Nullable;

public abstract class AbstractYandexRaspEntitySpecification<E extends IYandexRaspEntity, F extends IYandexRaspFilter> extends AbstractEntitySpecification<E, F> {
    public AbstractYandexRaspEntitySpecification(@Nullable F filter) {
        super(filter);
    }

    @Override
    protected @NonNull List<Pair<? super ICriteria, List<Attribute<?, ?>>>> getCriteria(@Nullable F filter) {
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
