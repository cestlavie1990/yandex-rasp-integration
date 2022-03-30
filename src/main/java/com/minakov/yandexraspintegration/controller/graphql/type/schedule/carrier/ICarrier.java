package com.minakov.yandexraspintegration.controller.graphql.type.schedule.carrier;

public interface ICarrier {
    Integer getCode();

    String getName();

    String getIataCode();

    String getIcaoCode();

    String getSirenaCode();
}