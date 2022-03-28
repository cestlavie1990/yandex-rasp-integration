package com.minakov.yandexraspintegration.feign.dto.yandex.rasp;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class PaginationDto {
    @NonNull
    private final Integer total;
    @NonNull
    private final Integer limit;
    @NonNull
    private final Integer offset;
}
