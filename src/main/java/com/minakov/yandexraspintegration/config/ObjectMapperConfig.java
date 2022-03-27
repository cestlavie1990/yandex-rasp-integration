package com.minakov.yandexraspintegration.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ObjectMapperConfig {

    @Primary
    @Bean
    public ObjectMapper objectMapper() {
        final ObjectMapper result = new ObjectMapper();
        result.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        result.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        result.configure(DeserializationFeature.ACCEPT_FLOAT_AS_INT, false);
        result.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        result.registerModule(new Jdk8Module());
        result.registerModule(new JavaTimeModule());
        result.registerModule(new ParameterNamesModule());

        return result;
    }

    @Bean
    public ObjectMapper snakeCaseMapper() {
        final ObjectMapper result = new ObjectMapper();
        result.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        result.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        result.configure(DeserializationFeature.ACCEPT_FLOAT_AS_INT, false);
        result.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        result.setPropertyNamingStrategy(new PropertyNamingStrategies.SnakeCaseStrategy());
        result.registerModule(new Jdk8Module());
        result.registerModule(new JavaTimeModule());
        result.registerModule(new ParameterNamesModule());

        return result;
    }
}
