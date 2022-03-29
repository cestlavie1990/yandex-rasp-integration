package com.minakov.yandexraspintegration.config;

import com.minakov.yandexraspintegration.controller.graphql.scalar.DateScalarCoercing;
import com.minakov.yandexraspintegration.controller.graphql.scalar.DateTimeScalarCoercing;
import graphql.schema.GraphQLScalarType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@Configuration
public class GraphQLConfig {
    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        final var dateScalar = GraphQLScalarType.newScalar().name("Date").coercing(new DateScalarCoercing()).build();
        final var dateTimeScalar =
                GraphQLScalarType.newScalar().name("DateTime").coercing(new DateTimeScalarCoercing()).build();

        return wiringBuilder -> wiringBuilder.scalar(dateScalar).scalar(dateTimeScalar);
    }
}
