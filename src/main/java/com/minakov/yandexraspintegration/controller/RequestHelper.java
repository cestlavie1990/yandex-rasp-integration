package com.minakov.yandexraspintegration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import lombok.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public record RequestHelper(@NonNull ObjectMapper objectMapper) {
    @Nullable
    public <T> T toObject(@Nullable final Map<String, Object> params, @NonNull final String key,
            @NonNull final Class<T> resultClass) {
        if (params == null) {
            return null;
        }
        return objectMapper.convertValue(params.get(key), resultClass);
    }
}
