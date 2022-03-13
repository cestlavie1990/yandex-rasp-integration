package com.minakov.yandexraspintegration.dto.feign.yandex.rasp;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Settlement {
    @NonNull
    private Code codes;
    @NonNull
    private String title;
    @NonNull
    private List<Station> stations;
}