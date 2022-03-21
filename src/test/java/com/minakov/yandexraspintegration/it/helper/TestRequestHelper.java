package com.minakov.yandexraspintegration.it.helper;

import static io.restassured.RestAssured.given;

import com.minakov.yandexraspintegration.config.properties.YandexRaspProperties;
import com.minakov.yandexraspintegration.it.controller.graphql.GraphQLQuery;
import com.minakov.yandexraspintegration.util.TestUtil;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import java.util.Map;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHeaders;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestRequestHelper {
    @NonNull
    private final YandexRaspProperties properties;

    private int serverPort;

    @NonNull
    public ValidatableResponse graphql(@NonNull final Resource query) {
        return graphql(query, null);
    }

    @NonNull
    public ValidatableResponse graphql(@NonNull final Resource query, @Nullable final Object variables) {
        return graphql(GraphQLQuery.builder().query(TestUtil.readResource(query)).variables(variables).build());
    }

    @NonNull
    public ValidatableResponse graphql(@NonNull final Resource query, @Nullable final Map<String, Object> variables) {
        return graphql(GraphQLQuery.builder().query(TestUtil.readResource(query)).variables(variables).build());
    }

    private ValidatableResponse graphql(@NonNull final GraphQLQuery query) {
        return given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, properties.getApiKey())
                .body(query)
                .when()
                .port(serverPort)
                .post("/graphql")
                .then();
    }

    @EventListener(WebServerInitializedEvent.class)
    public void onApplicationEvent(@NonNull final WebServerInitializedEvent event) {
        serverPort = event.getWebServer().getPort();
    }
}
