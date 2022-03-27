package com.minakov.yandexraspintegration.it.controller.graphql;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import com.minakov.yandexraspintegration.controller.graphql.input.code.CodeFilter;
import com.minakov.yandexraspintegration.controller.graphql.input.country.CountryFilter;
import com.minakov.yandexraspintegration.controller.graphql.input.filter.StringCriteria;
import com.minakov.yandexraspintegration.controller.graphql.input.filter.StringCriteriaValue;
import com.minakov.yandexraspintegration.controller.graphql.input.filter.UUIDCriteria;
import com.minakov.yandexraspintegration.controller.graphql.input.filter.UUIDCriteriaValue;
import com.minakov.yandexraspintegration.it.AbstractIT;
import com.minakov.yandexraspintegration.it.SpringBootIT;
import com.minakov.yandexraspintegration.it.helper.CountryTestHelper;
import com.minakov.yandexraspintegration.model.CountryEntity;
import com.minakov.yandexraspintegration.model.embedded.CodeEmbedded;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import org.assertj.core.util.Maps;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

@SpringBootIT
class CountryControllerQueryTest extends AbstractIT {
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
        final var title = countryTestHelper.get(UUID.fromString(id), CountryEntity::getTitle);

        requestHelper.graphql(countryQuery, Map.of("id", id))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.country.id", equalTo(id))
                .body("data.country.title", equalTo(title))
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
    void filterByIdIn() {
        final var id1 = countryTestHelper.create();
        final var id2 = countryTestHelper.create();
        final var id3 = countryTestHelper.create();

        requestHelper.graphql(countriesFilteredQuery, Map.of("filter", CountryFilter.builder()
                        .id(UUIDCriteria.builder().in(UUIDCriteriaValue.builder().values(List.of(id1, id2)).build()).build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.countries.id", hasItems(id1.toString(), id2.toString()))
                .body("data.countries.id", not(hasItems(id3.toString())));

        requestHelper.graphql(countriesFilteredQuery, Map.of("filter", CountryFilter.builder()
                        .id(UUIDCriteria.builder()
                                .in(UUIDCriteriaValue.builder().values(List.of(id1, id2)).inverse(true).build())
                                .build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.countries.id", not(hasItems(id1.toString(), id2.toString())))
                .body("data.countries.id", hasItems(id3.toString()));

        // @formatter:off
        requestHelper.graphql(countriesFilteredQuery, Map.of("filter", CountryFilter.builder()
                        .id(UUIDCriteria.builder()
                                .in(UUIDCriteriaValue.builder().values(List.of()).build())
                                .build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.countries", empty());
        // @formatter:on

        requestHelper.graphql(countriesFilteredQuery, Map.of("filter", CountryFilter.builder()
                        .id(UUIDCriteria.builder()
                                .in(UUIDCriteriaValue.builder().values(List.of()).inverse(true).build())
                                .build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.countries.id", hasItems(id1.toString(), id2.toString(), id3.toString()));

        requestHelper.graphql(countriesFilteredQuery, Maps.newHashMap("filter", null))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.countries.id", hasItems(id1.toString(), id2.toString(), id3.toString()));
    }

    @Test
    void filterByTitleIn() {
        final var id1 = countryTestHelper.create();
        final var id2 = countryTestHelper.create();
        final var id3 = countryTestHelper.create();

        final var title1 = Objects.requireNonNull(countryTestHelper.get(id1, CountryEntity::getTitle));
        final var title2 = Objects.requireNonNull(countryTestHelper.get(id2, CountryEntity::getTitle));

        requestHelper.graphql(countriesFilteredQuery, Map.of("filter", CountryFilter.builder()
                        .title(StringCriteria.builder()
                                .in(StringCriteriaValue.builder().values(List.of(title1, title2)).build())
                                .build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.countries.id", hasItems(id1.toString(), id2.toString()))
                .body("data.countries.id", not(hasItems(id3.toString())));

        requestHelper.graphql(countriesFilteredQuery, Map.of("filter", CountryFilter.builder()
                        .title(StringCriteria.builder()
                                .in(StringCriteriaValue.builder().values(List.of(title1, title2)).inverse(true).build())
                                .build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.countries.id", not(hasItems(id1.toString(), id2.toString())))
                .body("data.countries.id", hasItems(id3.toString()));

        // @formatter:off
        requestHelper.graphql(countriesFilteredQuery, Map.of("filter", CountryFilter.builder()
                        .title(StringCriteria.builder()
                                .in(StringCriteriaValue.builder().values(List.of()).build())
                                .build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.countries", empty());
        // @formatter:on

        requestHelper.graphql(countriesFilteredQuery, Map.of("filter", CountryFilter.builder()
                        .title(StringCriteria.builder()
                                .in(StringCriteriaValue.builder().values(List.of()).inverse(true).build())
                                .build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.countries.id", hasItems(id1.toString(), id2.toString(), id3.toString()));
    }

    @Test
    void filterByTitleLike() {
        final var id1 = countryTestHelper.create();
        final var id2 = countryTestHelper.create();

        countryTestHelper.update(id1, e -> e.setTitle("title-".concat(id1.toString())));
        countryTestHelper.update(id2, e -> e.setTitle("title-".concat(id2.toString())));

        final var likeTitle = "%".concat(id1.toString()).concat("%");

        requestHelper.graphql(countriesFilteredQuery, Map.of("filter", CountryFilter.builder()
                        .title(StringCriteria.builder()
                                .like(StringCriteriaValue.builder().values(List.of(likeTitle)).build())
                                .build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.countries.id", hasItems(id1.toString()))
                .body("data.countries.id", not(hasItems(id2.toString())));

        requestHelper.graphql(countriesFilteredQuery, Map.of("filter", CountryFilter.builder()
                        .title(StringCriteria.builder()
                                .like(StringCriteriaValue.builder().values(List.of(likeTitle)).inverse(true).build())
                                .build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.countries.id", not(hasItems(id1.toString())))
                .body("data.countries.id", hasItems(id2.toString()));

        requestHelper.graphql(countriesFilteredQuery, Map.of("filter", CountryFilter.builder()
                        .title(StringCriteria.builder().like(StringCriteriaValue.builder().values(List.of()).build()).build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.countries.id", not(hasItems(id1.toString(), id2.toString())));

        requestHelper.graphql(countriesFilteredQuery, Map.of("filter", CountryFilter.builder()
                        .title(StringCriteria.builder()
                                .like(StringCriteriaValue.builder().values(List.of()).inverse(true).build())
                                .build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.countries.id", hasItems(id1.toString(), id2.toString()));
    }

    @Test
    void filterByEsrCodeIn() {
        final var id1 = countryTestHelper.create();
        final var id2 = countryTestHelper.create();
        final var id3 = countryTestHelper.create();

        final var esrCode1 = "esr-code-".concat(id1.toString());
        final var esrCode2 = "esr-code-".concat(id2.toString());
        final var esrCode3 = "esr-code-".concat(id3.toString());

        countryTestHelper.update(id1, e -> e.setCode(CodeEmbedded.builder().esrCode(esrCode1).build()));
        countryTestHelper.update(id2, e -> e.setCode(CodeEmbedded.builder().esrCode(esrCode2).build()));
        countryTestHelper.update(id3, e -> e.setCode(CodeEmbedded.builder().esrCode(esrCode3).build()));

        requestHelper.graphql(countriesFilteredQuery, Map.of("filter", CountryFilter.builder()
                        .code(CodeFilter.builder()
                                .esrCode(StringCriteria.builder()
                                        .in(StringCriteriaValue.builder().values(List.of(esrCode1, esrCode2)).build())
                                        .build())
                                .build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.countries.id", hasItems(id1.toString(), id2.toString()))
                .body("data.countries.id", not(hasItems(id3.toString())));

        requestHelper.graphql(countriesFilteredQuery, Map.of("filter", CountryFilter.builder()
                        .code(CodeFilter.builder()
                                .esrCode(StringCriteria.builder()
                                        .in(StringCriteriaValue.builder()
                                                .values(List.of(esrCode1, esrCode2))
                                                .inverse(true)
                                                .build())
                                        .build())
                                .build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.countries.id", not(hasItems(id1.toString(), id2.toString())))
                .body("data.countries.id", hasItems(id3.toString()));

        // @formatter:off
        requestHelper.graphql(countriesFilteredQuery, Map.of("filter", CountryFilter.builder()
                        .code(CodeFilter.builder()
                                .esrCode(StringCriteria.builder()
                                        .in(StringCriteriaValue.builder()
                                                .values(List.of())
                                                .build())
                                        .build())
                                .build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.countries", empty());
        // @formatter:on

        requestHelper.graphql(countriesFilteredQuery, Map.of("filter", CountryFilter.builder()
                        .code(CodeFilter.builder()
                                .esrCode(StringCriteria.builder()
                                        .in(StringCriteriaValue.builder().values(List.of()).inverse(true).build())
                                        .build())
                                .build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.countries.id", hasItems(id1.toString(), id2.toString(), id3.toString()));
    }

    @Test
    void filterByYandexCodeIn() {
        final var id1 = countryTestHelper.create();
        final var id2 = countryTestHelper.create();
        final var id3 = countryTestHelper.create();

        final var yandexCode1 = "yandex-code-".concat(id1.toString());
        final var yandexCode2 = "yandex-code-".concat(id2.toString());
        final var yandexCode3 = "yandex-code-".concat(id3.toString());

        countryTestHelper.update(id1, e -> e.setCode(CodeEmbedded.builder().yandexCode(yandexCode1).build()));
        countryTestHelper.update(id2, e -> e.setCode(CodeEmbedded.builder().yandexCode(yandexCode2).build()));
        countryTestHelper.update(id3, e -> e.setCode(CodeEmbedded.builder().yandexCode(yandexCode3).build()));

        requestHelper.graphql(countriesFilteredQuery, Map.of("filter", CountryFilter.builder()
                        .code(CodeFilter.builder()
                                .yandexCode(StringCriteria.builder()
                                        .in(StringCriteriaValue.builder().values(List.of(yandexCode1, yandexCode2)).build())
                                        .build())
                                .build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.countries.id", hasItems(id1.toString(), id2.toString()))
                .body("data.countries.id", not(hasItems(id3.toString())));

        requestHelper.graphql(countriesFilteredQuery, Map.of("filter", CountryFilter.builder()
                        .code(CodeFilter.builder()
                                .yandexCode(StringCriteria.builder()
                                        .in(StringCriteriaValue.builder()
                                                .values(List.of(yandexCode1, yandexCode2))
                                                .inverse(true)
                                                .build())
                                        .build())
                                .build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.countries.id", not(hasItems(id1.toString(), id2.toString())))
                .body("data.countries.id", hasItems(id3.toString()));

        // @formatter:off
        requestHelper.graphql(countriesFilteredQuery, Map.of("filter", CountryFilter.builder()
                        .code(CodeFilter.builder()
                                .yandexCode(StringCriteria.builder()
                                        .in(StringCriteriaValue.builder()
                                                .values(List.of())
                                                .build())
                                        .build())
                                .build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.countries", empty());
        // @formatter:on

        requestHelper.graphql(countriesFilteredQuery, Map.of("filter", CountryFilter.builder()
                        .code(CodeFilter.builder()
                                .yandexCode(StringCriteria.builder()
                                        .in(StringCriteriaValue.builder().values(List.of()).inverse(true).build())
                                        .build())
                                .build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.countries.id", hasItems(id1.toString(), id2.toString(), id3.toString()));
    }

    @Test
    void filterByYandexBothCodes() {
        final var id1 = countryTestHelper.create();
        final var id2 = countryTestHelper.create();
        final var id3 = countryTestHelper.create();

        final var esrCode1 = "esr-code-".concat(id1.toString());
        final var esrCode2 = "esr-code-".concat(id2.toString());
        final var esrCode3 = "esr-code-".concat(id3.toString());

        final var yandexCode1 = "yandex-code-".concat(id1.toString());
        final var yandexCode2 = "yandex-code-".concat(id2.toString());
        final var yandexCode3 = "yandex-code-".concat(id3.toString());

        countryTestHelper.update(id1,
                e -> e.setCode(CodeEmbedded.builder().yandexCode(yandexCode1).esrCode(esrCode1).build()));
        countryTestHelper.update(id2,
                e -> e.setCode(CodeEmbedded.builder().yandexCode(yandexCode2).esrCode(esrCode2).build()));
        countryTestHelper.update(id3,
                e -> e.setCode(CodeEmbedded.builder().yandexCode(yandexCode3).esrCode(esrCode3).build()));

        requestHelper.graphql(countriesFilteredQuery, Map.of("filter", CountryFilter.builder()
                        .code(CodeFilter.builder()
                                .yandexCode(StringCriteria.builder()
                                        .in(StringCriteriaValue.builder().values(List.of(yandexCode1, yandexCode2)).build())
                                        .build())
                                .esrCode(StringCriteria.builder()
                                        .like(StringCriteriaValue.builder()
                                                .values(List.of("%".concat(id2.toString()).concat("%")))
                                                .build())
                                        .build())
                                .build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.countries.id", hasItems(id2.toString()))
                .body("data.countries.id", not(hasItems(id1.toString(), id3.toString())));
    }
}
