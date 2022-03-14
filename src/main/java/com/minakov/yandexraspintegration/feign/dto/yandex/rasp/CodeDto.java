package com.minakov.yandexraspintegration.feign.dto.yandex.rasp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CodeDto {
    @Nullable
    private String esrCode;
    @Nullable
    private String yandexCode;
}