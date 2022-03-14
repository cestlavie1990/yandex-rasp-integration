package com.minakov.yandexraspintegration.feign;

import com.minakov.yandexraspintegration.feign.dto.yandex.rasp.StationListDto;
import lombok.NonNull;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

public interface YandexRaspClient {
    String AUTH_HEADER = "Authorization";
    String DEFAULT_LANG = "ru_RU";
    String DEFAULT_JSON = "json";

    @GetMapping(value = "stations_list", consumes = MediaType.APPLICATION_JSON_VALUE)
    StationListDto getStationList(@RequestHeader(AUTH_HEADER) @NonNull String auth,
            @RequestParam(name = "lang", required = false, defaultValue = DEFAULT_LANG) String lang,
            @RequestParam(name = "format", required = false, defaultValue = DEFAULT_JSON) String json);
}
