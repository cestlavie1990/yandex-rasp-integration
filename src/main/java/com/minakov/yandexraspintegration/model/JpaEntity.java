package com.minakov.yandexraspintegration.model;

import java.io.Serializable;

public interface JpaEntity<ID extends Serializable> extends Serializable {
    ID getId();

    void setId(ID id);
}
