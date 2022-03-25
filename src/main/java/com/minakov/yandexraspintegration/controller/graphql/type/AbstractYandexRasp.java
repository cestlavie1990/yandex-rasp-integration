package com.minakov.yandexraspintegration.controller.graphql.type;

import com.minakov.yandexraspintegration.controller.graphql.type.code.Code;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@EqualsAndHashCode
public abstract class AbstractYandexRasp<ID> implements IGraphQLType {
    @NonNull
    private final ID id;
    @Builder.Default
    @NonNull
    private final Code code = Code.builder().build();
    @NonNull
    private final String title;
}
