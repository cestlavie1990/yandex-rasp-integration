package com.minakov.yandexraspintegration.config.feign;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minakov.yandexraspintegration.config.properties.YandexRaspProperties;
import com.minakov.yandexraspintegration.feign.YandexRaspClient;
import feign.Feign;
import feign.Request;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import java.util.concurrent.TimeUnit;
import lombok.NonNull;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class YandexRaspConfig {

    @Bean
    public YandexRaspClient yandexRaspClient(@NonNull final ObjectMapper objectMapper,
            @NonNull final YandexRaspProperties prop) {
        return Feign.builder()
                .client(new OkHttpClient())
                .encoder(new JacksonEncoder(objectMapper))
                .decoder(new JacksonDecoder(objectMapper))
                .options(new Request.Options(prop.getConnectTimeout(), TimeUnit.MILLISECONDS, prop.getReadTimeout(),
                        TimeUnit.MILLISECONDS, true))
                .logger(new Slf4jLogger(YandexRaspClient.class))
                .contract(new SpringMvcContract())
                .target(YandexRaspClient.class, prop.getUrl());
    }
}
