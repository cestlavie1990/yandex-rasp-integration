package com.minakov.yandexraspintegration.feign.dto.yandex.rasp.schedulestation;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;

@Getter
@Builder
@RequiredArgsConstructor
public class CarrierDto {
    @Nullable
    private final Integer code;
    @Nullable
    private final CarrierCodeDto codes;
    @Nullable
    private final String title;
}
