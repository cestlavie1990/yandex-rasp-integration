package com.minakov.yandexraspintegration.it.feign;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.minakov.yandexraspintegration.feign.YandexRaspClient;
import com.minakov.yandexraspintegration.it.AbstractIT;
import com.minakov.yandexraspintegration.it.SpringBootIt;
import com.minakov.yandexraspintegration.util.TestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

@SpringBootIt
class YandexRaspClientTest extends AbstractIT {
    @Value("classpath:data/feign/yandexrasp/stationlist/response.json")
    private Resource responseResource;

    @Autowired
    private YandexRaspClient client;

    @Test
    void getStationList() {
        wireMockServer.stubFor(
                get(urlPathEqualTo("/stations_list")).withHeader("Content-Type", equalTo("application/json"))
                        .withQueryParam("lang", equalTo("ru_RU"))
                        .withQueryParam("format", equalTo("json"))
                        .willReturn(aResponse().withHeader("Content-Type", "application/json")
                                .withBody(TestUtils.readResource(responseResource))
                                .withStatus(200)));

        {
            final var response = client.getStationList("ass", "ru_RU", "json");

            assertEquals(1, response.getCountries().size());

            final var country = response.getCountries().get(0);

            assertEquals("esr_code_l225", country.getCodes().getEsrCode());
            assertEquals("l225", country.getCodes().getYandexCode());
            assertEquals("Russia", country.getTitle());
            assertEquals(1, country.getRegions().size());

            final var region = country.getRegions().get(0);

            assertEquals("esr_code_r10174", region.getCodes().getEsrCode());
            assertEquals("r10174", region.getCodes().getYandexCode());
            assertEquals("St. Petersburg and Leningrad Region", region.getTitle());
            assertEquals(1, region.getSettlements().size());

            final var settlement = region.getSettlements().get(0);

            assertEquals("esr_code_c2", settlement.getCodes().getEsrCode());
            assertEquals("c2", settlement.getCodes().getYandexCode());
            assertEquals("St. Petersburg", settlement.getTitle());
            assertEquals(1, settlement.getStations().size());

            final var station = settlement.getStations().get(0);

            assertEquals("esr_code_s9600366", station.getCodes().getEsrCode());
            assertEquals("s9600366", station.getCodes().getYandexCode());
            assertEquals("LED", station.getTitle());
            assertEquals("direction_s9600366", station.getDirection());
            assertEquals("airport", station.getStationType());
            assertEquals("plane", station.getTransportType());
            assertEquals(59.799489, station.getLatitude());
            assertEquals(30.271525, station.getLongitude());
        }
    }
}
