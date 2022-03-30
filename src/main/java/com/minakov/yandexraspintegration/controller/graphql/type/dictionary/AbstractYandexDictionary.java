package com.minakov.yandexraspintegration.controller.graphql.type.dictionary;

import com.minakov.yandexraspintegration.controller.graphql.type.IGraphQLType;
import com.minakov.yandexraspintegration.controller.graphql.type.dictionary.code.Code;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import org.springframework.lang.Nullable;

@Getter
@SuperBuilder
@EqualsAndHashCode
public abstract class AbstractYandexDictionary<ID> implements IGraphQLType {
    @NonNull
    private final ID id;
    @Nullable
    private final Code code;
    @Nullable
    private final String title;
}
