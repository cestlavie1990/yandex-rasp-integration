package com.minakov.yandexraspintegration.controller.graphql.input.filter;

import java.util.List;
import lombok.NonNull;

public interface ICriteriaValue<T> {
    @NonNull List<T> getValues();

    @NonNull Boolean getInverse();
}
