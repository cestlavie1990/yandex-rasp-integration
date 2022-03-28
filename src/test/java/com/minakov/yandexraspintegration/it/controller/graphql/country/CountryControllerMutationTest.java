package com.minakov.yandexraspintegration.it.controller.graphql.country;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.minakov.yandexraspintegration.it.AbstractIT;
import com.minakov.yandexraspintegration.it.SpringBootIT;
import com.minakov.yandexraspintegration.it.helper.CountryTestHelper;
import com.minakov.yandexraspintegration.util.TestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

@SpringBootIT
class CountryControllerMutationTest extends AbstractIT {
    @Value("classpath:data/controller/graphql/country/refreshCountries.graphql")
    private Resource refreshQuery;
    @Value("classpath:data/controller/graphql/country/yandexrasp/response1.json")
    private Resource feignResponse;

    @Autowired
    private CountryTestHelper countryHelper;

    @Test
    void refresh() {
        wireMockServer.stubFor(
                get(urlPathEqualTo("/stations_list")).withHeader("Content-Type", WireMock.equalTo("application/json"))
                        .willReturn(aResponse().withHeader("Content-Type", "application/json")
                                .withBody(TestUtil.readResource(feignResponse))
                                .withStatus(200)));

        requestHelper.graphql(refreshQuery)
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.refresh", equalTo(1));

        {
            final var countries = countryHelper.getAll(c -> {
                assertEquals(1, c.getRegions().size());
                assertEquals(1, c.getRegions().get(0).getSettlements().size());
                assertEquals(1, c.getRegions().get(0).getSettlements().get(0).getStations().size());
                return c;
            });

            assertEquals(1, countries.size());
        }

        requestHelper.graphql(refreshQuery)
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.refresh", equalTo(1));

        {
            final var countries = countryHelper.getAll(c -> {
                assertEquals(1, c.getRegions().size());
                assertEquals(1, c.getRegions().get(0).getSettlements().size());
                assertEquals(1, c.getRegions().get(0).getSettlements().get(0).getStations().size());
                return c;
            });

            assertEquals(1, countries.size());
        }
    }
}
