package com.minakov.yandexraspintegration.controller.graphql.type;

import com.minakov.yandexraspintegration.controller.graphql.type.code.Code;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import org.springframework.lang.Nullable;

@Getter
@SuperBuilder
@EqualsAndHashCode
public abstract class AbstractYandexRasp<ID> implements IGraphQLType {
    @NonNull
    private final ID id;
    @Nullable
    private final Code code;
    @Nullable
    private final String title;
}
