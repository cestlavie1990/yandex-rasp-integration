package com.minakov.yandexraspintegration.controller.graphql.input.filter;

import com.minakov.yandexraspintegration.controller.graphql.input.code.CodeFilter;

public interface IYandexStationFilter extends IFilter {
    UUIDCriteria getId();

    StringCriteria getTitle();

    CodeFilter getCode();
}
