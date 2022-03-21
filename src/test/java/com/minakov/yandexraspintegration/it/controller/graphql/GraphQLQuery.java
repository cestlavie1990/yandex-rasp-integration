package com.minakov.yandexraspintegration.it.controller.graphql;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GraphQLQuery {
    private String query;
    private Object variables;
}