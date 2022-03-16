package com.minakov.yandexraspintegration.controller.graphql.type.code;

import com.minakov.yandexraspintegration.controller.graphql.type.IGraphQLType;
import lombok.Builder;
import lombok.Getter;
import org.springframework.lang.Nullable;

@Getter
@Builder
public class Code implements IGraphQLType {
    @Nullable
    private final String esrCode;
    @Nullable
    private final String yandexCode;
}
