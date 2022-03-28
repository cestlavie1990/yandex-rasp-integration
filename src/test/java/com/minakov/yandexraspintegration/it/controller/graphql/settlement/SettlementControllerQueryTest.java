package com.minakov.yandexraspintegration.it.controller.graphql.settlement;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import com.minakov.yandexraspintegration.controller.graphql.input.code.CodeFilter;
import com.minakov.yandexraspintegration.controller.graphql.input.filter.StringCriteria;
import com.minakov.yandexraspintegration.controller.graphql.input.filter.StringCriteriaValue;
import com.minakov.yandexraspintegration.controller.graphql.input.filter.UUIDCriteria;
import com.minakov.yandexraspintegration.controller.graphql.input.filter.UUIDCriteriaValue;
import com.minakov.yandexraspintegration.controller.graphql.input.region.RegionFilter;
import com.minakov.yandexraspintegration.controller.graphql.input.settlement.SettlementFilter;
import com.minakov.yandexraspintegration.it.AbstractIT;
import com.minakov.yandexraspintegration.it.SpringBootIT;
import com.minakov.yandexraspintegration.it.helper.SettlementTestHelper;
import com.minakov.yandexraspintegration.it.helper.StationTestHelper;
import com.minakov.yandexraspintegration.model.SettlementEntity;
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
class SettlementControllerQueryTest extends AbstractIT {
    @Value("classpath:data/controller/graphql/settlement/settlement.graphql")
    private Resource settlementQuery;
    @Value("classpath:data/controller/graphql/settlement/settlements.graphql")
    private Resource settlementsQuery;
    @Value("classpath:data/controller/graphql/settlement/settlements-filtered.graphql")
    private Resource settlementsFilteredQuery;

    @Autowired
    private SettlementTestHelper settlementHelper;
    @Autowired
    private StationTestHelper stationHelper;

    @Test
    void getById() {
        final var id = settlementHelper.create();
        final var title = settlementHelper.get(id, SettlementEntity::getTitle);
        final var regionId = Objects.requireNonNull(settlementHelper.get(id, SettlementEntity::getRegionId));
        final var stationId = stationHelper.create(id);

        requestHelper.graphql(settlementQuery, Map.of("id", id))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.settlement.id", equalTo(id.toString()))
                .body("data.settlement.title", equalTo(title))
                .body("data.settlement.code.esrCode", equalTo(SettlementTestHelper.Default.CODE.getEsrCode()))
                .body("data.settlement.code.yandexCode", equalTo(SettlementTestHelper.Default.CODE.getYandexCode()))
                .body("data.settlement.region.id", equalTo(regionId.toString()))
                .body("data.settlement.stations.id", containsInAnyOrder(stationId.toString()));
    }

    @Test
    void getAll() {
        final var id1 = settlementHelper.create().toString();
        final var id2 = settlementHelper.create().toString();

        requestHelper.graphql(settlementsQuery)
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.settlements.id", hasItems(id1, id2));
    }

    @Test
    void filterByIdIn() {
        final var id1 = settlementHelper.create();
        final var id2 = settlementHelper.create();
        final var id3 = settlementHelper.create();

        requestHelper.graphql(settlementsFilteredQuery, Map.of("filter", SettlementFilter.builder()
                        .id(UUIDCriteria.builder().in(UUIDCriteriaValue.builder().values(List.of(id1, id2)).build()).build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.settlements.id", hasItems(id1.toString(), id2.toString()))
                .body("data.settlements.id", not(hasItems(id3.toString())));

        requestHelper.graphql(settlementsFilteredQuery, Map.of("filter", SettlementFilter.builder()
                        .id(UUIDCriteria.builder()
                                .in(UUIDCriteriaValue.builder().values(List.of(id1, id2)).inverse(true).build())
                                .build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.settlements.id", not(hasItems(id1.toString(), id2.toString())))
                .body("data.settlements.id", hasItems(id3.toString()));

        // @formatter:off
        requestHelper.graphql(settlementsFilteredQuery, Map.of("filter", SettlementFilter.builder()
                        .id(UUIDCriteria.builder()
                                .in(UUIDCriteriaValue.builder().values(List.of()).build())
                                .build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.settlements", empty());
        // @formatter:on

        requestHelper.graphql(settlementsFilteredQuery, Map.of("filter", SettlementFilter.builder()
                        .id(UUIDCriteria.builder()
                                .in(UUIDCriteriaValue.builder().values(List.of()).inverse(true).build())
                                .build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.settlements.id", hasItems(id1.toString(), id2.toString(), id3.toString()));

        requestHelper.graphql(settlementsFilteredQuery, Maps.newHashMap("filter", null))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.settlements.id", hasItems(id1.toString(), id2.toString(), id3.toString()));
    }

    @Test
    void filterByTitleIn() {
        final var id1 = settlementHelper.create();
        final var id2 = settlementHelper.create();
        final var id3 = settlementHelper.create();

        final var title1 = Objects.requireNonNull(settlementHelper.get(id1, SettlementEntity::getTitle));
        final var title2 = Objects.requireNonNull(settlementHelper.get(id2, SettlementEntity::getTitle));

        requestHelper.graphql(settlementsFilteredQuery, Map.of("filter", SettlementFilter.builder()
                        .title(StringCriteria.builder()
                                .in(StringCriteriaValue.builder().values(List.of(title1, title2)).build())
                                .build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.settlements.id", hasItems(id1.toString(), id2.toString()))
                .body("data.settlements.id", not(hasItems(id3.toString())));

        requestHelper.graphql(settlementsFilteredQuery, Map.of("filter", SettlementFilter.builder()
                        .title(StringCriteria.builder()
                                .in(StringCriteriaValue.builder().values(List.of(title1, title2)).inverse(true).build())
                                .build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.settlements.id", not(hasItems(id1.toString(), id2.toString())))
                .body("data.settlements.id", hasItems(id3.toString()));

        // @formatter:off
        requestHelper.graphql(settlementsFilteredQuery, Map.of("filter", SettlementFilter.builder()
                        .title(StringCriteria.builder()
                                .in(StringCriteriaValue.builder().values(List.of()).build())
                                .build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.settlements", empty());
        // @formatter:on

        requestHelper.graphql(settlementsFilteredQuery, Map.of("filter", SettlementFilter.builder()
                        .title(StringCriteria.builder()
                                .in(StringCriteriaValue.builder().values(List.of()).inverse(true).build())
                                .build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.settlements.id", hasItems(id1.toString(), id2.toString(), id3.toString()));
    }

    @Test
    void filterByTitleLike() {
        final var id1 = settlementHelper.create();
        final var id2 = settlementHelper.create();

        settlementHelper.update(id1, e -> e.setTitle("title-".concat(id1.toString())));
        settlementHelper.update(id2, e -> e.setTitle("title-".concat(id2.toString())));

        final var likeTitle = "%".concat(id1.toString()).concat("%");

        requestHelper.graphql(settlementsFilteredQuery, Map.of("filter", SettlementFilter.builder()
                        .title(StringCriteria.builder()
                                .like(StringCriteriaValue.builder().values(List.of(likeTitle)).build())
                                .build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.settlements.id", hasItems(id1.toString()))
                .body("data.settlements.id", not(hasItems(id2.toString())));

        requestHelper.graphql(settlementsFilteredQuery, Map.of("filter", SettlementFilter.builder()
                        .title(StringCriteria.builder()
                                .like(StringCriteriaValue.builder().values(List.of(likeTitle)).inverse(true).build())
                                .build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.settlements.id", not(hasItems(id1.toString())))
                .body("data.settlements.id", hasItems(id2.toString()));

        requestHelper.graphql(settlementsFilteredQuery, Map.of("filter", SettlementFilter.builder()
                        .title(StringCriteria.builder().like(StringCriteriaValue.builder().values(List.of()).build()).build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.settlements.id", not(hasItems(id1.toString(), id2.toString())));

        requestHelper.graphql(settlementsFilteredQuery, Map.of("filter", SettlementFilter.builder()
                        .title(StringCriteria.builder()
                                .like(StringCriteriaValue.builder().values(List.of()).inverse(true).build())
                                .build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.settlements.id", hasItems(id1.toString(), id2.toString()));
    }

    @Test
    void filterByEsrCodeIn() {
        final var id1 = settlementHelper.create();
        final var id2 = settlementHelper.create();
        final var id3 = settlementHelper.create();

        final var esrCode1 = "esr-code-".concat(id1.toString());
        final var esrCode2 = "esr-code-".concat(id2.toString());
        final var esrCode3 = "esr-code-".concat(id3.toString());

        settlementHelper.update(id1, e -> e.setCode(CodeEmbedded.builder().esrCode(esrCode1).build()));
        settlementHelper.update(id2, e -> e.setCode(CodeEmbedded.builder().esrCode(esrCode2).build()));
        settlementHelper.update(id3, e -> e.setCode(CodeEmbedded.builder().esrCode(esrCode3).build()));

        requestHelper.graphql(settlementsFilteredQuery, Map.of("filter", SettlementFilter.builder()
                        .code(CodeFilter.builder()
                                .esrCode(StringCriteria.builder()
                                        .in(StringCriteriaValue.builder().values(List.of(esrCode1, esrCode2)).build())
                                        .build())
                                .build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.settlements.id", hasItems(id1.toString(), id2.toString()))
                .body("data.settlements.id", not(hasItems(id3.toString())));

        requestHelper.graphql(settlementsFilteredQuery, Map.of("filter", SettlementFilter.builder()
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
                .body("data.settlements.id", not(hasItems(id1.toString(), id2.toString())))
                .body("data.settlements.id", hasItems(id3.toString()));

        // @formatter:off
        requestHelper.graphql(settlementsFilteredQuery, Map.of("filter", SettlementFilter.builder()
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
                .body("data.settlements", empty());
        // @formatter:on

        requestHelper.graphql(settlementsFilteredQuery, Map.of("filter", SettlementFilter.builder()
                        .code(CodeFilter.builder()
                                .esrCode(StringCriteria.builder()
                                        .in(StringCriteriaValue.builder().values(List.of()).inverse(true).build())
                                        .build())
                                .build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.settlements.id", hasItems(id1.toString(), id2.toString(), id3.toString()));
    }

    @Test
    void filterByYandexCodeIn() {
        final var id1 = settlementHelper.create();
        final var id2 = settlementHelper.create();
        final var id3 = settlementHelper.create();

        final var yandexCode1 = "yandex-code-".concat(id1.toString());
        final var yandexCode2 = "yandex-code-".concat(id2.toString());
        final var yandexCode3 = "yandex-code-".concat(id3.toString());

        settlementHelper.update(id1, e -> e.setCode(CodeEmbedded.builder().yandexCode(yandexCode1).build()));
        settlementHelper.update(id2, e -> e.setCode(CodeEmbedded.builder().yandexCode(yandexCode2).build()));
        settlementHelper.update(id3, e -> e.setCode(CodeEmbedded.builder().yandexCode(yandexCode3).build()));

        requestHelper.graphql(settlementsFilteredQuery, Map.of("filter", SettlementFilter.builder()
                        .code(CodeFilter.builder()
                                .yandexCode(StringCriteria.builder()
                                        .in(StringCriteriaValue.builder().values(List.of(yandexCode1, yandexCode2)).build())
                                        .build())
                                .build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.settlements.id", hasItems(id1.toString(), id2.toString()))
                .body("data.settlements.id", not(hasItems(id3.toString())));

        requestHelper.graphql(settlementsFilteredQuery, Map.of("filter", SettlementFilter.builder()
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
                .body("data.settlements.id", not(hasItems(id1.toString(), id2.toString())))
                .body("data.settlements.id", hasItems(id3.toString()));

        // @formatter:off
        requestHelper.graphql(settlementsFilteredQuery, Map.of("filter", SettlementFilter.builder()
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
                .body("data.settlements", empty());
        // @formatter:on

        requestHelper.graphql(settlementsFilteredQuery, Map.of("filter", SettlementFilter.builder()
                        .code(CodeFilter.builder()
                                .yandexCode(StringCriteria.builder()
                                        .in(StringCriteriaValue.builder().values(List.of()).inverse(true).build())
                                        .build())
                                .build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.settlements.id", hasItems(id1.toString(), id2.toString(), id3.toString()));
    }

    @Test
    void filterByYandexBothCodes() {
        final var id1 = settlementHelper.create();
        final var id2 = settlementHelper.create();
        final var id3 = settlementHelper.create();

        final var esrCode1 = "esr-code-".concat(id1.toString());
        final var esrCode2 = "esr-code-".concat(id2.toString());
        final var esrCode3 = "esr-code-".concat(id3.toString());

        final var yandexCode1 = "yandex-code-".concat(id1.toString());
        final var yandexCode2 = "yandex-code-".concat(id2.toString());
        final var yandexCode3 = "yandex-code-".concat(id3.toString());

        settlementHelper.update(id1,
                e -> e.setCode(CodeEmbedded.builder().yandexCode(yandexCode1).esrCode(esrCode1).build()));
        settlementHelper.update(id2,
                e -> e.setCode(CodeEmbedded.builder().yandexCode(yandexCode2).esrCode(esrCode2).build()));
        settlementHelper.update(id3,
                e -> e.setCode(CodeEmbedded.builder().yandexCode(yandexCode3).esrCode(esrCode3).build()));

        requestHelper.graphql(settlementsFilteredQuery, Map.of("filter", SettlementFilter.builder()
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
                .body("data.settlements.id", hasItems(id2.toString()))
                .body("data.settlements.id", not(hasItems(id1.toString(), id3.toString())));
    }

    @Test
    void filterByRegionIdIn() {
        final var id1 = settlementHelper.create();
        final var id2 = settlementHelper.create();
        final var id3 = settlementHelper.create();

        final var countryId1 = Objects.requireNonNull(settlementHelper.get(id1, SettlementEntity::getRegionId));
        final var countryId2 = Objects.requireNonNull(settlementHelper.get(id2, SettlementEntity::getRegionId));

        requestHelper.graphql(settlementsFilteredQuery, Map.of("filter", SettlementFilter.builder()
                        .region(RegionFilter.builder()
                                .id(UUIDCriteria.builder()
                                        .in(UUIDCriteriaValue.builder().values(List.of(countryId1, countryId2)).build())
                                        .build())
                                .build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.settlements.id", hasItems(id1.toString(), id2.toString()))
                .body("data.settlements.id", not(hasItems(id3.toString())));
    }
}
