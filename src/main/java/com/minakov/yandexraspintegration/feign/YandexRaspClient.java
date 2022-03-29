package com.minakov.yandexraspintegration.feign;

import com.minakov.yandexraspintegration.feign.dto.yandex.rasp.schedulestation.ScheduleStationDto;
import com.minakov.yandexraspintegration.feign.dto.yandex.rasp.stationlist.StationListDto;
import java.time.LocalDate;
import java.util.List;
import lombok.NonNull;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

public interface YandexRaspClient {
    String AUTH_HEADER = "Authorization";
    String DEFAULT_LANG = "ru_RU";
    String DEFAULT_JSON = "json";

    /**
     * @param auth - authorization token
     * @param lang - response language codes (ISO 639), supports: ru_RU (default), uk_UA
     * @param format - supports: json (default), xml
     * @return ScheduleStationDto
     */
    @GetMapping(value = "stations_list", consumes = MediaType.APPLICATION_JSON_VALUE)
    StationListDto getStationList(@RequestHeader(AUTH_HEADER) @NonNull String auth,
            @RequestParam(name = "lang", required = false, defaultValue = DEFAULT_LANG) String lang,
            @RequestParam(name = "format", required = false, defaultValue = DEFAULT_JSON) String format);

    /**
     * @param auth - authorization token
     * @param stationCode - code of station (default in yandex system)
     * @param systemCode - coding system, supports: yandex (default), iata, sirena, express, esr
     * @param event - supports: arrival or departure
     * @param date - date in ISO8601 (YYYY-MM-DD)
     * @param direction - available direction codes for station
     * @param showSystems - station system code in response, supports: yandex, esr, all
     * @param resultTimezone - timezone for dates in response
     * @param transportTypes - supports: plane, train, suburban, bus, water, helicopter
     * @param lang - response language codes (ISO 639), supports: ru_RU (default), uk_UA
     * @param format - supports: json (default), xml
     * @return ScheduleStationDto
     */
    @GetMapping(value = "schedule", consumes = MediaType.APPLICATION_JSON_VALUE)
    ScheduleStationDto getScheduleByStation(@RequestHeader(AUTH_HEADER) @NonNull String auth,
            @RequestParam(name = "station") @NonNull String stationCode,
            @RequestParam(name = "event", required = false) String event,
            @RequestParam(name = "date", required = false) LocalDate date,
            @RequestParam(name = "system", required = false) String systemCode,
            @RequestParam(name = "direction", required = false) String direction,
            @RequestParam(name = "show_systems", required = false) String showSystems,
            @RequestParam(name = "result_timezone", required = false) String resultTimezone,
            @RequestParam(name = "transport_types", required = false) List<String> transportTypes,
            @RequestParam(name = "lang", required = false, defaultValue = DEFAULT_LANG) String lang,
            @RequestParam(name = "format", required = false, defaultValue = DEFAULT_JSON) String format);
}
