package com.minakov.yandexraspintegration.controller.graphql.type.schedule;

import java.time.OffsetDateTime;

public interface IScheduledObject {
    String getUid();

    String getTitle();

    OffsetDateTime getArrival();

    OffsetDateTime getDeparture();

    TransportType getTransportType();

    Boolean getIsFuzzy();
}
