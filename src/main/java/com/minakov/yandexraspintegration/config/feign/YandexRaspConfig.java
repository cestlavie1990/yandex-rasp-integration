package com.minakov.yandexraspintegration.config.feign;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
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
    public YandexRaspClient yandexRaspClient(@NonNull final YandexRaspProperties prop) {
        final var mapper = createMapper();

        return Feign.builder()
                .client(new OkHttpClient())
                .encoder(new JacksonEncoder(mapper))
                .decoder(new JacksonDecoder(mapper))
                .options(new Request.Options(prop.getConnectTimeout(), TimeUnit.MILLISECONDS, prop.getReadTimeout(),
                        TimeUnit.MILLISECONDS, true))
                .logger(new Slf4jLogger(YandexRaspClient.class))
                .contract(new SpringMvcContract())
                .target(YandexRaspClient.class, prop.getUrl());
    }

    private ObjectMapper createMapper() {
        final var result = new ObjectMapper();
        result.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        result.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        result.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false);
        result.configure(DeserializationFeature.ACCEPT_FLOAT_AS_INT, false);
        result.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        result.setPropertyNamingStrategy(new PropertyNamingStrategies.SnakeCaseStrategy());
        result.registerModule(new Jdk8Module());
        result.registerModule(new JavaTimeModule());
        result.registerModule(new ParameterNamesModule());

        return result;
    }
}
