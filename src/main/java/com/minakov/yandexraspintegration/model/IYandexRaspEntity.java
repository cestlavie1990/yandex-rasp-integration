package com.minakov.yandexraspintegration.model;

import com.minakov.yandexraspintegration.model.embedded.CodeEmbedded;
import java.util.UUID;

public interface IYandexRaspEntity extends IEntity<UUID> {
    CodeEmbedded getCode();

    String getTitle();
}
