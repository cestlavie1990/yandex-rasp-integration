package com.minakov.yandexraspintegration.controller.graphql.input.filter;

import java.util.List;

public interface IFilter<F> {
    List<F> getOr();

    List<F> getAnd();

    List<F> getNot();
}
