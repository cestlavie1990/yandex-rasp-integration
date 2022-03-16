package com.minakov.yandexraspintegration.service;

import java.util.List;
import java.util.stream.Collectors;

public interface IMapper<E, D> {
    D toDto(final E source);

    default List<D> toDto(final List<E> sources) {
        return sources.stream().map(this::toDto).collect(Collectors.toList());
    }
}
