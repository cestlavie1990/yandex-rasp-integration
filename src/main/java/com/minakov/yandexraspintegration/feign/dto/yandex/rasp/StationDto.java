package com.minakov.yandexraspintegration.feign.dto.yandex.rasp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StationDto {
    @NonNull
    private CodeDto codes;
    @NonNull
    private String title;
    @NonNull
    private String direction;
    @NonNull
    private String stationType;
    @NonNull
    private String transportType;
    @NonNull
    private Double latitude;
    @NonNull
    private Double longitude;
}