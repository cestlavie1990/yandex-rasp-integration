package com.minakov.yandexraspintegration.controller.graphql;

import com.minakov.yandexraspintegration.controller.graphql.type.region.Region;
import com.minakov.yandexraspintegration.controller.graphql.type.settlement.Settlement;
import com.minakov.yandexraspintegration.service.region.RegionService;
import com.minakov.yandexraspintegration.service.settlement.SettlementService;
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
public class SettlementController {
    @NonNull
    private final SettlementService service;
    @NonNull
    private final RegionService regionService;

    @QueryMapping
    public Settlement settlement(@Argument final UUID id) {
        return service.getById(id);
    }

    @QueryMapping
    public List<Settlement> settlements() {
        return service.getAll();
    }

    @BatchMapping
    public Map<Settlement, Region> region(final List<Settlement> settlements) {
        final var regions =
                regionService.getAllById(settlements.stream().map(Settlement::getRegionId).collect(Collectors.toSet()))
                        .stream()
                        .collect(Collectors.toMap(Region::getId, Function.identity()));

        return settlements.stream()
                .collect(HashMap::new, (m, v) -> m.put(v, regions.get(v.getRegionId().toString())), HashMap::putAll);
    }
}
