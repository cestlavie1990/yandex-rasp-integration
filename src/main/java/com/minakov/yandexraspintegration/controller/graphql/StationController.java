package com.minakov.yandexraspintegration.controller.graphql;

import com.minakov.yandexraspintegration.controller.RequestHelper;
import com.minakov.yandexraspintegration.controller.graphql.input.station.StationFilter;
import com.minakov.yandexraspintegration.controller.graphql.type.settlement.Settlement;
import com.minakov.yandexraspintegration.controller.graphql.type.station.Station;
import com.minakov.yandexraspintegration.service.settlement.SettlementService;
import com.minakov.yandexraspintegration.service.station.StationMapper;
import com.minakov.yandexraspintegration.service.station.StationService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class StationController {
    @NonNull
    private final StationService service;
    @NonNull
    private final SettlementService settlementService;
    @NonNull
    private final RequestHelper requestHelper;

    @QueryMapping
    public Station station(@Argument final UUID id) {
        return service.getById(id);
    }

    @QueryMapping
    public List<Station> stations(@Argument final Map<String, Object> params) {
        final var filter = requestHelper.toObject(params, "filter", StationFilter.class);

        return service.getAll(filter, StationMapper.INSTANCE::map);
    }

    @BatchMapping
    public Map<Station, Settlement> settlement(final List<Station> stations) {
        final var settlements = settlementService.getAllById(
                        stations.stream().map(s -> UUID.fromString(s.getSettlementId())).collect(Collectors.toSet()))
                .stream()
                .collect(Collectors.toMap(Settlement::getId, Function.identity()));

        return stations.stream()
                .collect(HashMap::new, (m, v) -> m.put(v, settlements.get(v.getSettlementId())), HashMap::putAll);
    }
}
