package com.minakov.yandexraspintegration.feign.dto.yandex.rasp;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SettlementDto {
    @NonNull
    private CodeDto codes;
    @NonNull
    private String title;
    @NonNull
    private List<StationDto> stations;
}