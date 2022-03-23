package com.minakov.yandexraspintegration.feign.dto.yandex.rasp;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CountryDto {
    private final CodeDto codes;
    private final String title;
    private final List<RegionDto> regions;
}