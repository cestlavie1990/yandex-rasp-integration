package com.minakov.yandexraspintegration.model;

import java.io.Serializable;

public interface JpaEntity<ID> extends Serializable {
    ID getId();

    void setId(ID id);
}
