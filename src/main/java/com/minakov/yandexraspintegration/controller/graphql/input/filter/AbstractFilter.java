package com.minakov.yandexraspintegration.controller.graphql.input.filter;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.lang.Nullable;

@Getter
@SuperBuilder
@RequiredArgsConstructor
public class AbstractFilter<F> implements IFilter<F> {
    @Nullable
    private final List<F> or;
    @Nullable
    private final List<F> and;
    @Nullable
    private final List<F> not;
}
