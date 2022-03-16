package com.minakov.yandexraspintegration.controller.graphql.type;

import com.minakov.yandexraspintegration.controller.graphql.type.code.Code;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public abstract class AbstractYandexRasp<ID> implements IGraphQLType {
    @NonNull
    private final ID id;
    @NonNull
    private final Code code;
    @NonNull
    private final String title;
}
