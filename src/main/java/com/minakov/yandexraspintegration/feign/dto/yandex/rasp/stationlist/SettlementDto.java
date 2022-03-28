package com.minakov.yandexraspintegration.feign.dto.yandex.rasp.stationlist;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class SettlementDto {
    private final CodeDto codes;
    private final String title;
    private final List<StationDto> stations;
}