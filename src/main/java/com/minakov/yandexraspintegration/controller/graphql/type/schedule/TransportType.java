package com.minakov.yandexraspintegration.controller.graphql.type.schedule;

import lombok.Getter;
import lombok.NonNull;

public enum TransportType {
    PLANE("plane"),
    TRAIN("train"),
    SUBURBAN("suburban"),
    BUS("bus"),
    WATER("water"),
    HELICOPTER("helicopter");

    @Getter
    private final String internalName;

    TransportType(@NonNull final String internalName) {
        this.internalName = internalName;
    }

    @NonNull
    public static TransportType byInternalName(@NonNull final String internalName) {
        if (PLANE.internalName.equals(internalName)) {
            return PLANE;
        } else if (TRAIN.internalName.equals(internalName)) {
            return TRAIN;
        } else if (SUBURBAN.internalName.equals(internalName)) {
            return SUBURBAN;
        } else if (BUS.internalName.equals(internalName)) {
            return BUS;
        } else if (WATER.internalName.equals(internalName)) {
            return WATER;
        } else if (HELICOPTER.internalName.equals(internalName)) {
            return HELICOPTER;
        }
        throw new IllegalArgumentException(internalName);
    }
}
