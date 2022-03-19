package com.minakov.yandexraspintegration.controller.graphql.input.filter;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseFilter<F> implements IFilter<F> {
    @Nullable
    private List<F> or;
    @Nullable
    private List<F> and;
    @Nullable
    private List<F> not;
}
