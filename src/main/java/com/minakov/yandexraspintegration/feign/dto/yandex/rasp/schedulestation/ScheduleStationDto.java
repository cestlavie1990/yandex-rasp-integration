package com.minakov.yandexraspintegration.feign.dto.yandex.rasp.schedulestation;

import com.minakov.yandexraspintegration.feign.dto.yandex.rasp.PaginationDto;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;

@Getter
@Builder
@RequiredArgsConstructor
public class ScheduleStationDto {
    @Nullable
    private final LocalDate date;
    @NonNull
    private final PaginationDto pagination;
    @NonNull
    private final StationDto station;
    @Nullable
    private final DirectionDto scheduleDirection;
    @Nullable
    private final List<ScheduleDto> schedule;
    @Nullable
    private final List<ScheduleDto> intervalSchedule;
    @Nullable
    private final List<DirectionDto> directions;
}
