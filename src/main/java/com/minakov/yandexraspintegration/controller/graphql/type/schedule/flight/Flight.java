package com.minakov.yandexraspintegration.controller.graphql.type.schedule.flight;

import com.minakov.yandexraspintegration.controller.graphql.type.schedule.IScheduledObject;
import com.minakov.yandexraspintegration.controller.graphql.type.schedule.TransportType;
import com.minakov.yandexraspintegration.controller.graphql.type.schedule.carrier.Airline;
import java.time.OffsetDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.lang.Nullable;

@Getter
@Builder
public class Flight implements IScheduledObject {
    @NonNull
    private final String uid;
    @Nullable
    private final String title;
    @Nullable
    private final String number;
    @Nullable
    private final String vehicle;
    @Nullable
    private final String terminal;
    @Nullable
    private final String platform;
    @Nullable
    private final OffsetDateTime arrival;
    @Nullable
    private final OffsetDateTime departure;
    @Nullable
    private final TransportType transportType;
    @Nullable
    private final Airline airline;
    @Nullable
    private final Boolean isFuzzy;
}
