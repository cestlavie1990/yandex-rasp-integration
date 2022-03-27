package com.minakov.yandexraspintegration.controller.graphql.input.code;

import com.minakov.yandexraspintegration.controller.graphql.input.filter.IFilter;
import com.minakov.yandexraspintegration.controller.graphql.input.filter.StringCriteria;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CodeFilter implements IFilter<CodeFilter> {
    @Nullable
    private StringCriteria esrCode;
    @Nullable
    private StringCriteria yandexCode;
}
