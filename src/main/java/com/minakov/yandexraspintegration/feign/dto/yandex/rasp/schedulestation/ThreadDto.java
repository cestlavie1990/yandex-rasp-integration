package com.minakov.yandexraspintegration.feign.dto.yandex.rasp.schedulestation;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;

@Getter
@Builder
@RequiredArgsConstructor
public class ThreadDto {
    @Nullable
    private final String uid;
    @Nullable
    private final String title;
    @Nullable
    private final String number;
    @Nullable
    private final String shortTitle;
    @Nullable
    private final CarrierDto carrier;
    @Nullable
    private final IntervalDto interval;
    @Nullable
    private final String transportType;
    @Nullable
    private final String vehicle;
    @Nullable
    private final TransportSubtypeDto transportSubtype;
    @Nullable
    private final String expressType;
}
