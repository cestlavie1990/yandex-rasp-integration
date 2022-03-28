package com.minakov.yandexraspintegration.it.controller.graphql.station;

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
import com.minakov.yandexraspintegration.controller.graphql.input.settlement.SettlementFilter;
import com.minakov.yandexraspintegration.controller.graphql.input.station.StationFilter;
import com.minakov.yandexraspintegration.it.AbstractIT;
import com.minakov.yandexraspintegration.it.SpringBootIT;
import com.minakov.yandexraspintegration.it.helper.StationTestHelper;
import com.minakov.yandexraspintegration.model.StationEntity;
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
class StationControllerQueryTest extends AbstractIT {
    @Value("classpath:data/controller/graphql/station/station.graphql")
    private Resource stationQuery;
    @Value("classpath:data/controller/graphql/station/stations.graphql")
    private Resource stationsQuery;
    @Value("classpath:data/controller/graphql/station/stations-filtered.graphql")
    private Resource stationsFilteredQuery;

    @Autowired
    private StationTestHelper stationHelper;

    @Test
    void getById() {
        final var id = stationHelper.create();
        final var title = stationHelper.get(id, StationEntity::getTitle);
        final var settlementId = Objects.requireNonNull(stationHelper.get(id, StationEntity::getSettlementId));

        requestHelper.graphql(stationQuery, Map.of("id", id))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.station.id", equalTo(id.toString()))
                .body("data.station.title", equalTo(title))
                .body("data.station.code.esrCode", equalTo(StationTestHelper.Default.CODE.getEsrCode()))
                .body("data.station.code.yandexCode", equalTo(StationTestHelper.Default.CODE.getYandexCode()))
                .body("data.station.settlement.id", equalTo(settlementId.toString()));
    }

    @Test
    void getAll() {
        final var id1 = stationHelper.create().toString();
        final var id2 = stationHelper.create().toString();

        requestHelper.graphql(stationsQuery)
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.stations.id", hasItems(id1, id2));
    }

    @Test
    void filterByIdIn() {
        final var id1 = stationHelper.create();
        final var id2 = stationHelper.create();
        final var id3 = stationHelper.create();

        requestHelper.graphql(stationsFilteredQuery, Map.of("filter", StationFilter.builder()
                        .id(UUIDCriteria.builder().in(UUIDCriteriaValue.builder().values(List.of(id1, id2)).build()).build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.stations.id", hasItems(id1.toString(), id2.toString()))
                .body("data.stations.id", not(hasItems(id3.toString())));

        requestHelper.graphql(stationsFilteredQuery, Map.of("filter", StationFilter.builder()
                        .id(UUIDCriteria.builder()
                                .in(UUIDCriteriaValue.builder().values(List.of(id1, id2)).inverse(true).build())
                                .build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.stations.id", not(hasItems(id1.toString(), id2.toString())))
                .body("data.stations.id", hasItems(id3.toString()));

        // @formatter:off
        requestHelper.graphql(stationsFilteredQuery, Map.of("filter", StationFilter.builder()
                        .id(UUIDCriteria.builder()
                                .in(UUIDCriteriaValue.builder().values(List.of()).build())
                                .build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.stations", empty());
        // @formatter:on

        requestHelper.graphql(stationsFilteredQuery, Map.of("filter", StationFilter.builder()
                        .id(UUIDCriteria.builder()
                                .in(UUIDCriteriaValue.builder().values(List.of()).inverse(true).build())
                                .build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.stations.id", hasItems(id1.toString(), id2.toString(), id3.toString()));

        requestHelper.graphql(stationsFilteredQuery, Maps.newHashMap("filter", null))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.stations.id", hasItems(id1.toString(), id2.toString(), id3.toString()));
    }

    @Test
    void filterByTitleIn() {
        final var id1 = stationHelper.create();
        final var id2 = stationHelper.create();
        final var id3 = stationHelper.create();

        final var title1 = Objects.requireNonNull(stationHelper.get(id1, StationEntity::getTitle));
        final var title2 = Objects.requireNonNull(stationHelper.get(id2, StationEntity::getTitle));

        requestHelper.graphql(stationsFilteredQuery, Map.of("filter", StationFilter.builder()
                        .title(StringCriteria.builder()
                                .in(StringCriteriaValue.builder().values(List.of(title1, title2)).build())
                                .build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.stations.id", hasItems(id1.toString(), id2.toString()))
                .body("data.stations.id", not(hasItems(id3.toString())));

        requestHelper.graphql(stationsFilteredQuery, Map.of("filter", StationFilter.builder()
                        .title(StringCriteria.builder()
                                .in(StringCriteriaValue.builder().values(List.of(title1, title2)).inverse(true).build())
                                .build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.stations.id", not(hasItems(id1.toString(), id2.toString())))
                .body("data.stations.id", hasItems(id3.toString()));

        // @formatter:off
        requestHelper.graphql(stationsFilteredQuery, Map.of("filter", StationFilter.builder()
                        .title(StringCriteria.builder()
                                .in(StringCriteriaValue.builder().values(List.of()).build())
                                .build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.stations", empty());
        // @formatter:on

        requestHelper.graphql(stationsFilteredQuery, Map.of("filter", StationFilter.builder()
                        .title(StringCriteria.builder()
                                .in(StringCriteriaValue.builder().values(List.of()).inverse(true).build())
                                .build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.stations.id", hasItems(id1.toString(), id2.toString(), id3.toString()));
    }

    @Test
    void filterByTitleLike() {
        final var id1 = stationHelper.create();
        final var id2 = stationHelper.create();

        stationHelper.update(id1, e -> e.setTitle("title-".concat(id1.toString())));
        stationHelper.update(id2, e -> e.setTitle("title-".concat(id2.toString())));

        final var likeTitle = "%".concat(id1.toString()).concat("%");

        requestHelper.graphql(stationsFilteredQuery, Map.of("filter", StationFilter.builder()
                        .title(StringCriteria.builder()
                                .like(StringCriteriaValue.builder().values(List.of(likeTitle)).build())
                                .build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.stations.id", hasItems(id1.toString()))
                .body("data.stations.id", not(hasItems(id2.toString())));

        requestHelper.graphql(stationsFilteredQuery, Map.of("filter", StationFilter.builder()
                        .title(StringCriteria.builder()
                                .like(StringCriteriaValue.builder().values(List.of(likeTitle)).inverse(true).build())
                                .build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.stations.id", not(hasItems(id1.toString())))
                .body("data.stations.id", hasItems(id2.toString()));

        requestHelper.graphql(stationsFilteredQuery, Map.of("filter", StationFilter.builder()
                        .title(StringCriteria.builder().like(StringCriteriaValue.builder().values(List.of()).build()).build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.stations.id", not(hasItems(id1.toString(), id2.toString())));

        requestHelper.graphql(stationsFilteredQuery, Map.of("filter", StationFilter.builder()
                        .title(StringCriteria.builder()
                                .like(StringCriteriaValue.builder().values(List.of()).inverse(true).build())
                                .build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.stations.id", hasItems(id1.toString(), id2.toString()));
    }

    @Test
    void filterByEsrCodeIn() {
        final var id1 = stationHelper.create();
        final var id2 = stationHelper.create();
        final var id3 = stationHelper.create();

        final var esrCode1 = "esr-code-".concat(id1.toString());
        final var esrCode2 = "esr-code-".concat(id2.toString());
        final var esrCode3 = "esr-code-".concat(id3.toString());

        stationHelper.update(id1, e -> e.setCode(CodeEmbedded.builder().esrCode(esrCode1).build()));
        stationHelper.update(id2, e -> e.setCode(CodeEmbedded.builder().esrCode(esrCode2).build()));
        stationHelper.update(id3, e -> e.setCode(CodeEmbedded.builder().esrCode(esrCode3).build()));

        requestHelper.graphql(stationsFilteredQuery, Map.of("filter", StationFilter.builder()
                        .code(CodeFilter.builder()
                                .esrCode(StringCriteria.builder()
                                        .in(StringCriteriaValue.builder().values(List.of(esrCode1, esrCode2)).build())
                                        .build())
                                .build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.stations.id", hasItems(id1.toString(), id2.toString()))
                .body("data.stations.id", not(hasItems(id3.toString())));

        requestHelper.graphql(stationsFilteredQuery, Map.of("filter", StationFilter.builder()
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
                .body("data.stations.id", not(hasItems(id1.toString(), id2.toString())))
                .body("data.stations.id", hasItems(id3.toString()));

        // @formatter:off
        requestHelper.graphql(stationsFilteredQuery, Map.of("filter", StationFilter.builder()
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
                .body("data.stations", empty());
        // @formatter:on

        requestHelper.graphql(stationsFilteredQuery, Map.of("filter", StationFilter.builder()
                        .code(CodeFilter.builder()
                                .esrCode(StringCriteria.builder()
                                        .in(StringCriteriaValue.builder().values(List.of()).inverse(true).build())
                                        .build())
                                .build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.stations.id", hasItems(id1.toString(), id2.toString(), id3.toString()));
    }

    @Test
    void filterByYandexCodeIn() {
        final var id1 = stationHelper.create();
        final var id2 = stationHelper.create();
        final var id3 = stationHelper.create();

        final var yandexCode1 = "yandex-code-".concat(id1.toString());
        final var yandexCode2 = "yandex-code-".concat(id2.toString());
        final var yandexCode3 = "yandex-code-".concat(id3.toString());

        stationHelper.update(id1, e -> e.setCode(CodeEmbedded.builder().yandexCode(yandexCode1).build()));
        stationHelper.update(id2, e -> e.setCode(CodeEmbedded.builder().yandexCode(yandexCode2).build()));
        stationHelper.update(id3, e -> e.setCode(CodeEmbedded.builder().yandexCode(yandexCode3).build()));

        requestHelper.graphql(stationsFilteredQuery, Map.of("filter", StationFilter.builder()
                        .code(CodeFilter.builder()
                                .yandexCode(StringCriteria.builder()
                                        .in(StringCriteriaValue.builder().values(List.of(yandexCode1, yandexCode2)).build())
                                        .build())
                                .build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.stations.id", hasItems(id1.toString(), id2.toString()))
                .body("data.stations.id", not(hasItems(id3.toString())));

        requestHelper.graphql(stationsFilteredQuery, Map.of("filter", StationFilter.builder()
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
                .body("data.stations.id", not(hasItems(id1.toString(), id2.toString())))
                .body("data.stations.id", hasItems(id3.toString()));

        // @formatter:off
        requestHelper.graphql(stationsFilteredQuery, Map.of("filter", StationFilter.builder()
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
                .body("data.stations", empty());
        // @formatter:on

        requestHelper.graphql(stationsFilteredQuery, Map.of("filter", StationFilter.builder()
                        .code(CodeFilter.builder()
                                .yandexCode(StringCriteria.builder()
                                        .in(StringCriteriaValue.builder().values(List.of()).inverse(true).build())
                                        .build())
                                .build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.stations.id", hasItems(id1.toString(), id2.toString(), id3.toString()));
    }

    @Test
    void filterByYandexBothCodes() {
        final var id1 = stationHelper.create();
        final var id2 = stationHelper.create();
        final var id3 = stationHelper.create();

        final var esrCode1 = "esr-code-".concat(id1.toString());
        final var esrCode2 = "esr-code-".concat(id2.toString());
        final var esrCode3 = "esr-code-".concat(id3.toString());

        final var yandexCode1 = "yandex-code-".concat(id1.toString());
        final var yandexCode2 = "yandex-code-".concat(id2.toString());
        final var yandexCode3 = "yandex-code-".concat(id3.toString());

        stationHelper.update(id1,
                e -> e.setCode(CodeEmbedded.builder().yandexCode(yandexCode1).esrCode(esrCode1).build()));
        stationHelper.update(id2,
                e -> e.setCode(CodeEmbedded.builder().yandexCode(yandexCode2).esrCode(esrCode2).build()));
        stationHelper.update(id3,
                e -> e.setCode(CodeEmbedded.builder().yandexCode(yandexCode3).esrCode(esrCode3).build()));

        requestHelper.graphql(stationsFilteredQuery, Map.of("filter", StationFilter.builder()
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
                .body("data.stations.id", hasItems(id2.toString()))
                .body("data.stations.id", not(hasItems(id1.toString(), id3.toString())));
    }

    @Test
    void filterByRegionIdIn() {
        final var id1 = stationHelper.create();
        final var id2 = stationHelper.create();
        final var id3 = stationHelper.create();

        final var settlementId1 = Objects.requireNonNull(stationHelper.get(id1, StationEntity::getSettlementId));
        final var settlementId2 = Objects.requireNonNull(stationHelper.get(id2, StationEntity::getSettlementId));

        requestHelper.graphql(stationsFilteredQuery, Map.of("filter", StationFilter.builder()
                        .settlement(SettlementFilter.builder()
                                .id(UUIDCriteria.builder()
                                        .in(UUIDCriteriaValue.builder().values(List.of(settlementId1, settlementId2)).build())
                                        .build())
                                .build())
                        .build()))
                .statusCode(200)
                .assertThat()
                .body("errors", nullValue())
                .body("data.stations.id", hasItems(id1.toString(), id2.toString()))
                .body("data.stations.id", not(hasItems(id3.toString())));
    }
}
