package com.minakov.yandexraspintegration.feign.dto.yandex.rasp.schedulestation;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;

@Getter
@Builder
@RequiredArgsConstructor
public class DirectionDto {
    @Nullable
    private final String code;
    @Nullable
    private final String title;
}
