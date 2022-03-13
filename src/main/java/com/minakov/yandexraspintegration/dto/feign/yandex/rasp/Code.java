package com.minakov.yandexraspintegration.dto.feign.yandex.rasp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Code {
    @Nullable
    private String esrCode;
    @Nullable
    private String yandexCode;
}