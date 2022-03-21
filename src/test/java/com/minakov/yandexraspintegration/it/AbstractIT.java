package com.minakov.yandexraspintegration.it;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.minakov.yandexraspintegration.it.helper.TestRequestHelper;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;

@AutoConfigureWireMock
public abstract class AbstractIT {
    protected static final UUID FAKE_UUID = UUID.randomUUID();

    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected TestRequestHelper requestHelper;
    @Autowired
    protected WireMockServer wireMockServer;
}
