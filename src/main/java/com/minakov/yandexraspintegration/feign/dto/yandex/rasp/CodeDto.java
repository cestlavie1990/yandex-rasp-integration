package com.minakov.yandexraspintegration.feign.dto.yandex.rasp;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CodeDto {
    private final String esrCode;
    private final String yandexCode;
}