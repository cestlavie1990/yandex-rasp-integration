package com.minakov.yandexraspintegration.feign.dto.yandex.rasp;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class CodeDto {
    private final String esrCode;
    private final String yandexCode;
}