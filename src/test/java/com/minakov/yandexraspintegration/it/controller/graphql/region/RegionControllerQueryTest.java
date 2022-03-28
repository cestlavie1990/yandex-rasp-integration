package com.minakov.yandexraspintegration.it.controller.graphql.region;

import static org.hamcrest.Matchers.containsInAnyOrder;
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
import com.minakov.yandexraspintegration.controller.graphql.input.region.RegionFilter;
import com.minakov.yandexraspintegration.it.AbstractIT;
import com.minakov.yandexraspintegration.it.SpringBootIT;
import com.minakov.yandexraspintegration.it.helper.RegionTestHelper;
import com.minakov.yandexraspintegration.it.helper.SettlementTestHelper;
import com.minakov.yandexraspintegration.model.RegionEntity;
import com.minakov.yandexraspintegration.model.embedded.CodeEmbedded;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.assertj.core.util.Maps;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

@SpringBootIT
class RegionControllerQueryTest extends AbstractIT {
    @Value("classpath:data/controller/graphql/region/region.graphql")
    private Resource regionQuery;
    @Value("classpath:data/controller/graphql/region/regions.graphql")
    private Resource regionsQuery;
    @Value("classpath:data/controller/graphql/region/regions-filtered.graphql")
    private Resource regionsFilteredQuery;

    @Autowired
    private RegionTestHelper regionHelper;
    @Autowired
    private SettlementTestHelper settlementHelper;

    @Test
    void getById() {
        final var id = regionHelper.create();
        final var title = regionHelper.get(id, RegionEntity::getTitle);
        final var countryId = Objects.requireNonNull(regionHelper.get(id, RegionEntity::getCountryId));
        final var settlementId = settlementHelper.create(id);

        requestHelper.graphql(regionQuery, Map.of("id", id))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.region.id", equalTo(id.toString()))
                .body("data.region.title", equalTo(title))
                .body("data.region.code.esrCode", equalTo(RegionTestHelper.Default.CODE.getEsrCode()))
                .body("data.region.code.yandexCode", equalTo(RegionTestHelper.Default.CODE.getYandexCode()))
                .body("data.region.country.id", equalTo(countryId.toString()))
                .body("data.region.settlements.id", containsInAnyOrder(settlementId.toString()));
    }

    @Test
    void getAll() {
        final var id1 = regionHelper.create().toString();
        final var id2 = regionHelper.create().toString();

        requestHelper.graphql(regionsQuery)
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.regions.id", hasItems(id1, id2));
    }

    @Test
    void filterByIdIn() {
        final var id1 = regionHelper.create();
        final var id2 = regionHelper.create();
        final var id3 = regionHelper.create();

        requestHelper.graphql(regionsFilteredQuery, Map.of("filter", RegionFilter.builder()
                        .id(UUIDCriteria.builder().in(UUIDCriteriaValue.builder().values(List.of(id1, id2)).build()).build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.regions.id", hasItems(id1.toString(), id2.toString()))
                .body("data.regions.id", not(hasItems(id3.toString())));

        requestHelper.graphql(regionsFilteredQuery, Map.of("filter", RegionFilter.builder()
                        .id(UUIDCriteria.builder()
                                .in(UUIDCriteriaValue.builder().values(List.of(id1, id2)).inverse(true).build())
                                .build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.regions.id", not(hasItems(id1.toString(), id2.toString())))
                .body("data.regions.id", hasItems(id3.toString()));

        // @formatter:off
        requestHelper.graphql(regionsFilteredQuery, Map.of("filter", RegionFilter.builder()
                        .id(UUIDCriteria.builder()
                                .in(UUIDCriteriaValue.builder().values(List.of()).build())
                                .build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.regions", empty());
        // @formatter:on

        requestHelper.graphql(regionsFilteredQuery, Map.of("filter", RegionFilter.builder()
                        .id(UUIDCriteria.builder()
                                .in(UUIDCriteriaValue.builder().values(List.of()).inverse(true).build())
                                .build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.regions.id", hasItems(id1.toString(), id2.toString(), id3.toString()));

        requestHelper.graphql(regionsFilteredQuery, Maps.newHashMap("filter", null))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.regions.id", hasItems(id1.toString(), id2.toString(), id3.toString()));
    }

    @Test
    void filterByTitleIn() {
        final var id1 = regionHelper.create();
        final var id2 = regionHelper.create();
        final var id3 = regionHelper.create();

        final var title1 = Objects.requireNonNull(regionHelper.get(id1, RegionEntity::getTitle));
        final var title2 = Objects.requireNonNull(regionHelper.get(id2, RegionEntity::getTitle));

        requestHelper.graphql(regionsFilteredQuery, Map.of("filter", RegionFilter.builder()
                        .title(StringCriteria.builder()
                                .in(StringCriteriaValue.builder().values(List.of(title1, title2)).build())
                                .build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.regions.id", hasItems(id1.toString(), id2.toString()))
                .body("data.regions.id", not(hasItems(id3.toString())));

        requestHelper.graphql(regionsFilteredQuery, Map.of("filter", RegionFilter.builder()
                        .title(StringCriteria.builder()
                                .in(StringCriteriaValue.builder().values(List.of(title1, title2)).inverse(true).build())
                                .build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.regions.id", not(hasItems(id1.toString(), id2.toString())))
                .body("data.regions.id", hasItems(id3.toString()));

        // @formatter:off
        requestHelper.graphql(regionsFilteredQuery, Map.of("filter", RegionFilter.builder()
                        .title(StringCriteria.builder()
                                .in(StringCriteriaValue.builder().values(List.of()).build())
                                .build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.regions", empty());
        // @formatter:on

        requestHelper.graphql(regionsFilteredQuery, Map.of("filter", RegionFilter.builder()
                        .title(StringCriteria.builder()
                                .in(StringCriteriaValue.builder().values(List.of()).inverse(true).build())
                                .build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.regions.id", hasItems(id1.toString(), id2.toString(), id3.toString()));
    }

    @Test
    void filterByTitleLike() {
        final var id1 = regionHelper.create();
        final var id2 = regionHelper.create();

        regionHelper.update(id1, e -> e.setTitle("title-".concat(id1.toString())));
        regionHelper.update(id2, e -> e.setTitle("title-".concat(id2.toString())));

        final var likeTitle = "%".concat(id1.toString()).concat("%");

        requestHelper.graphql(regionsFilteredQuery, Map.of("filter", RegionFilter.builder()
                        .title(StringCriteria.builder()
                                .like(StringCriteriaValue.builder().values(List.of(likeTitle)).build())
                                .build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.regions.id", hasItems(id1.toString()))
                .body("data.regions.id", not(hasItems(id2.toString())));

        requestHelper.graphql(regionsFilteredQuery, Map.of("filter", RegionFilter.builder()
                        .title(StringCriteria.builder()
                                .like(StringCriteriaValue.builder().values(List.of(likeTitle)).inverse(true).build())
                                .build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.regions.id", not(hasItems(id1.toString())))
                .body("data.regions.id", hasItems(id2.toString()));

        requestHelper.graphql(regionsFilteredQuery, Map.of("filter", RegionFilter.builder()
                        .title(StringCriteria.builder().like(StringCriteriaValue.builder().values(List.of()).build()).build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.regions.id", not(hasItems(id1.toString(), id2.toString())));

        requestHelper.graphql(regionsFilteredQuery, Map.of("filter", RegionFilter.builder()
                        .title(StringCriteria.builder()
                                .like(StringCriteriaValue.builder().values(List.of()).inverse(true).build())
                                .build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.regions.id", hasItems(id1.toString(), id2.toString()));
    }

    @Test
    void filterByEsrCodeIn() {
        final var id1 = regionHelper.create();
        final var id2 = regionHelper.create();
        final var id3 = regionHelper.create();

        final var esrCode1 = "esr-code-".concat(id1.toString());
        final var esrCode2 = "esr-code-".concat(id2.toString());
        final var esrCode3 = "esr-code-".concat(id3.toString());

        regionHelper.update(id1, e -> e.setCode(CodeEmbedded.builder().esrCode(esrCode1).build()));
        regionHelper.update(id2, e -> e.setCode(CodeEmbedded.builder().esrCode(esrCode2).build()));
        regionHelper.update(id3, e -> e.setCode(CodeEmbedded.builder().esrCode(esrCode3).build()));

        requestHelper.graphql(regionsFilteredQuery, Map.of("filter", RegionFilter.builder()
                        .code(CodeFilter.builder()
                                .esrCode(StringCriteria.builder()
                                        .in(StringCriteriaValue.builder().values(List.of(esrCode1, esrCode2)).build())
                                        .build())
                                .build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.regions.id", hasItems(id1.toString(), id2.toString()))
                .body("data.regions.id", not(hasItems(id3.toString())));

        requestHelper.graphql(regionsFilteredQuery, Map.of("filter", RegionFilter.builder()
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
                .body("data.regions.id", not(hasItems(id1.toString(), id2.toString())))
                .body("data.regions.id", hasItems(id3.toString()));

        // @formatter:off
        requestHelper.graphql(regionsFilteredQuery, Map.of("filter", RegionFilter.builder()
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
                .body("data.regions", empty());
        // @formatter:on

        requestHelper.graphql(regionsFilteredQuery, Map.of("filter", RegionFilter.builder()
                        .code(CodeFilter.builder()
                                .esrCode(StringCriteria.builder()
                                        .in(StringCriteriaValue.builder().values(List.of()).inverse(true).build())
                                        .build())
                                .build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.regions.id", hasItems(id1.toString(), id2.toString(), id3.toString()));
    }

    @Test
    void filterByYandexCodeIn() {
        final var id1 = regionHelper.create();
        final var id2 = regionHelper.create();
        final var id3 = regionHelper.create();

        final var yandexCode1 = "yandex-code-".concat(id1.toString());
        final var yandexCode2 = "yandex-code-".concat(id2.toString());
        final var yandexCode3 = "yandex-code-".concat(id3.toString());

        regionHelper.update(id1, e -> e.setCode(CodeEmbedded.builder().yandexCode(yandexCode1).build()));
        regionHelper.update(id2, e -> e.setCode(CodeEmbedded.builder().yandexCode(yandexCode2).build()));
        regionHelper.update(id3, e -> e.setCode(CodeEmbedded.builder().yandexCode(yandexCode3).build()));

        requestHelper.graphql(regionsFilteredQuery, Map.of("filter", RegionFilter.builder()
                        .code(CodeFilter.builder()
                                .yandexCode(StringCriteria.builder()
                                        .in(StringCriteriaValue.builder().values(List.of(yandexCode1, yandexCode2)).build())
                                        .build())
                                .build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.regions.id", hasItems(id1.toString(), id2.toString()))
                .body("data.regions.id", not(hasItems(id3.toString())));

        requestHelper.graphql(regionsFilteredQuery, Map.of("filter", RegionFilter.builder()
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
                .body("data.regions.id", not(hasItems(id1.toString(), id2.toString())))
                .body("data.regions.id", hasItems(id3.toString()));

        // @formatter:off
        requestHelper.graphql(regionsFilteredQuery, Map.of("filter", RegionFilter.builder()
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
                .body("data.regions", empty());
        // @formatter:on

        requestHelper.graphql(regionsFilteredQuery, Map.of("filter", RegionFilter.builder()
                        .code(CodeFilter.builder()
                                .yandexCode(StringCriteria.builder()
                                        .in(StringCriteriaValue.builder().values(List.of()).inverse(true).build())
                                        .build())
                                .build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.regions.id", hasItems(id1.toString(), id2.toString(), id3.toString()));
    }

    @Test
    void filterByYandexBothCodes() {
        final var id1 = regionHelper.create();
        final var id2 = regionHelper.create();
        final var id3 = regionHelper.create();

        final var esrCode1 = "esr-code-".concat(id1.toString());
        final var esrCode2 = "esr-code-".concat(id2.toString());
        final var esrCode3 = "esr-code-".concat(id3.toString());

        final var yandexCode1 = "yandex-code-".concat(id1.toString());
        final var yandexCode2 = "yandex-code-".concat(id2.toString());
        final var yandexCode3 = "yandex-code-".concat(id3.toString());

        regionHelper.update(id1,
                e -> e.setCode(CodeEmbedded.builder().yandexCode(yandexCode1).esrCode(esrCode1).build()));
        regionHelper.update(id2,
                e -> e.setCode(CodeEmbedded.builder().yandexCode(yandexCode2).esrCode(esrCode2).build()));
        regionHelper.update(id3,
                e -> e.setCode(CodeEmbedded.builder().yandexCode(yandexCode3).esrCode(esrCode3).build()));

        requestHelper.graphql(regionsFilteredQuery, Map.of("filter", RegionFilter.builder()
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
                .body("data.regions.id", hasItems(id2.toString()))
                .body("data.regions.id", not(hasItems(id1.toString(), id3.toString())));
    }

    @Test
    void filterByCountryIdIn() {
        final var id1 = regionHelper.create();
        final var id2 = regionHelper.create();
        final var id3 = regionHelper.create();

        final var countryId1 = Objects.requireNonNull(regionHelper.get(id1, RegionEntity::getCountryId));
        final var countryId2 = Objects.requireNonNull(regionHelper.get(id2, RegionEntity::getCountryId));

        requestHelper.graphql(regionsFilteredQuery, Map.of("filter", RegionFilter.builder()
                        .country(CountryFilter.builder()
                                .id(UUIDCriteria.builder()
                                        .in(UUIDCriteriaValue.builder().values(List.of(countryId1, countryId2)).build())
                                        .build())
                                .build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.regions.id", hasItems(id1.toString(), id2.toString()))
                .body("data.regions.id", not(hasItems(id3.toString())));
    }
}
