package com.minakov.yandexraspintegration.it.controller.graphql;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import com.minakov.yandexraspintegration.controller.graphql.input.country.CountryFilter;
import com.minakov.yandexraspintegration.controller.graphql.input.filter.StringCriteria;
import com.minakov.yandexraspintegration.controller.graphql.input.filter.StringCriteriaValue;
import com.minakov.yandexraspintegration.it.AbstractIT;
import com.minakov.yandexraspintegration.it.SpringBootIT;
import com.minakov.yandexraspintegration.it.helper.CountryTestHelper;
import java.util.List;
import java.util.Map;
import org.assertj.core.util.Maps;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

@SpringBootIT
class CountryControllerTest extends AbstractIT {
    @Value("classpath:data/controller/graphql/country/country.graphql")
    private Resource countryQuery;
    @Value("classpath:data/controller/graphql/country/countries.graphql")
    private Resource countriesQuery;
    @Value("classpath:data/controller/graphql/country/countries-filtered.graphql")
    private Resource countriesFilteredQuery;

    @Autowired
    private CountryTestHelper countryTestHelper;

    @Test
    void getById() {
        final var id = countryTestHelper.create().toString();

        requestHelper.graphql(countryQuery, Map.of("id", id))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.country.id", equalTo(id))
                .body("data.country.title", equalTo(CountryTestHelper.Default.TITLE))
                .body("data.country.code.esrCode", equalTo(CountryTestHelper.Default.CODE.getEsrCode()))
                .body("data.country.code.yandexCode", equalTo(CountryTestHelper.Default.CODE.getYandexCode()));
    }

    @Test
    void getAll() {
        final var id1 = countryTestHelper.create().toString();
        final var id2 = countryTestHelper.create().toString();

        requestHelper.graphql(countriesQuery)
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.countries.id", hasItems(id1, id2));
    }

    @Test
    void filterById() {
        final var id1 = countryTestHelper.create().toString();
        final var id2 = countryTestHelper.create().toString();
        final var id3 = countryTestHelper.create().toString();

        requestHelper.graphql(countriesFilteredQuery, Map.of("filter", CountryFilter.builder()
                        .id(StringCriteria.builder()
                                .in(StringCriteriaValue.builder().values(List.of(id1, id2)).build())
                                .build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.countries.id", hasItems(id1, id2))
                .body("data.countries.id", not(hasItems(id3)));

        requestHelper.graphql(countriesFilteredQuery, Map.of("filter", CountryFilter.builder()
                        .id(StringCriteria.builder()
                                .in(StringCriteriaValue.builder().values(List.of(id1, id2)).inverse(true).build())
                                .build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.countries.id", not(hasItems(id1, id2)))
                .body("data.countries.id", hasItems(id3));

        // @formatter:off
        requestHelper.graphql(countriesFilteredQuery, Map.of("filter", CountryFilter.builder()
                        .id(StringCriteria.builder()
                                .in(StringCriteriaValue.builder().values(List.of()).build())
                                .build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.countries", empty());
        // @formatter:on

        requestHelper.graphql(countriesFilteredQuery, Map.of("filter", CountryFilter.builder()
                        .id(StringCriteria.builder()
                                .in(StringCriteriaValue.builder().values(List.of()).inverse(true).build())
                                .build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.countries.id", hasItems(id1, id2, id3));

        requestHelper.graphql(countriesFilteredQuery, Maps.newHashMap("filter", null))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.countries.id", hasItems(id1, id2, id3));
    }
}