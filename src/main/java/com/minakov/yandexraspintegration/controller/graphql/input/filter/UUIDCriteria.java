package com.minakov.yandexraspintegration.controller.graphql.input.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UUIDCriteria implements ICriteria {
    @Nullable
    private UUIDCriteriaValue in;
}
