package com.minakov.yandexraspintegration.feign.dto.yandex.rasp.schedulestation;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;

@Getter
@Builder
@RequiredArgsConstructor
public class IntervalDto {
    @Nullable
    private final String density;
    @Nullable
    private final LocalDateTime beginTime;
    @Nullable
    private final LocalDateTime endTime;
}
