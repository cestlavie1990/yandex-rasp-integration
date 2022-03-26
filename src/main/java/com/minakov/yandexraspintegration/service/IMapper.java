package com.minakov.yandexraspintegration.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public interface IMapper<E, D> {
    D map(final E source);

    default List<D> map(final List<E> sources) {
        if (sources == null) {
            return Collections.emptyList();
        }
        return sources.stream().map(this::map).collect(Collectors.toList());
    }
}
