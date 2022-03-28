package com.minakov.yandexraspintegration.service;

import com.minakov.yandexraspintegration.controller.graphql.input.filter.ICriteria;
import com.minakov.yandexraspintegration.controller.graphql.input.filter.IYandexStationFilter;
import com.minakov.yandexraspintegration.model.CountryEntity_;
import com.minakov.yandexraspintegration.model.IYandexStationEntity;
import com.minakov.yandexraspintegration.model.embedded.CodeEmbedded_;
import com.minakov.yandexraspintegration.service.filter.AbstractEntitySpecification;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.metamodel.Attribute;
import lombok.NonNull;
import org.springframework.data.util.Pair;
import org.springframework.lang.Nullable;

public abstract class AbstractYandexStationEntitySpecification<E extends IYandexStationEntity, F extends IYandexStationFilter> extends AbstractEntitySpecification<E, F> {
    public AbstractYandexStationEntitySpecification(@Nullable F filter) {
        super(filter);
    }

    @Override
    public @NonNull List<Pair<? super ICriteria, LinkedList<Attribute<?, ?>>>> getCriteria(@Nullable F filter) {
        final var criteria = new ArrayList<Pair<? super ICriteria, LinkedList<Attribute<?, ?>>>>();

        if (filter != null) {
            if (filter.getId() != null) {
                criteria.add(Pair.of(filter.getId(), new LinkedList<>() {{
                    add(CountryEntity_.id);
                }}));
            }
            if (filter.getTitle() != null) {
                criteria.add(Pair.of(filter.getTitle(), new LinkedList<>() {{
                    add(CountryEntity_.title);
                }}));
            }
            if (filter.getCode() != null) {
                final var code = filter.getCode();
                if (code.getEsrCode() != null) {
                    criteria.add(Pair.of(code.getEsrCode(), new LinkedList<>() {{
                        add(CountryEntity_.code);
                        add(CodeEmbedded_.esrCode);
                    }}));
                }
                if (code.getYandexCode() != null) {
                    criteria.add(Pair.of(code.getYandexCode(), new LinkedList<>() {{
                        add(CountryEntity_.code);
                        add(CodeEmbedded_.yandexCode);
                    }}));
                }
            }
        }

        return criteria;
    }
}
