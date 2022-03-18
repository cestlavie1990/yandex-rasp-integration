package com.minakov.yandexraspintegration.controller.graphql.input.filter;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.springframework.lang.Nullable;

@Getter
@Builder
public class StringCriteria implements ICriteria {
    @Nullable
    private final String like;
    @Nullable
    private final List<String> in;
}
