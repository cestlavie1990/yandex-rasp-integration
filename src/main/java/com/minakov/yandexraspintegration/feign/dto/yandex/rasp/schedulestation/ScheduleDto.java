package com.minakov.yandexraspintegration.feign.dto.yandex.rasp.schedulestation;

import java.time.OffsetDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;

@Getter
@Builder
@RequiredArgsConstructor
public class ScheduleDto {
    @Nullable
    private final OffsetDateTime arrival;
    @Nullable
    private final OffsetDateTime departure;
    @NonNull
    private final ThreadDto thread;
    @Nullable
    private final Boolean isFuzzy;
    @Nullable
    private final String days;
    @Nullable
    private final String stops;
    @Nullable
    private final String terminal;
    @Nullable
    private final String platform;
    @Nullable
    private final String exceptDays;
}
