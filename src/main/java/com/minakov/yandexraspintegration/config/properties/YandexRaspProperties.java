package com.minakov.yandexraspintegration.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "app.feign.client.yandex.rasp")
public class YandexRaspProperties {
    private String apiKey;
    private String url;
    private Integer connectTimeout;
    private Integer readTimeout;
}
