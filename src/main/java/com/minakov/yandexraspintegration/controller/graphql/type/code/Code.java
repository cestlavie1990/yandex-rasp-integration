package com.minakov.yandexraspintegration.controller.graphql.type.code;

import com.minakov.yandexraspintegration.controller.graphql.type.IGraphQLType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.lang.Nullable;

@Getter
@Builder
@EqualsAndHashCode
public class Code implements IGraphQLType {
    @Nullable
    private final String esrCode;
    @Nullable
    private final String yandexCode;
}
