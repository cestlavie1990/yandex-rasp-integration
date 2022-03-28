package com.minakov.yandexraspintegration.controller.graphql.input.station;

import com.minakov.yandexraspintegration.controller.graphql.input.code.CodeFilter;
import com.minakov.yandexraspintegration.controller.graphql.input.filter.IYandexStationFilter;
import com.minakov.yandexraspintegration.controller.graphql.input.filter.StringCriteria;
import com.minakov.yandexraspintegration.controller.graphql.input.filter.UUIDCriteria;
import com.minakov.yandexraspintegration.controller.graphql.input.settlement.SettlementFilter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StationFilter implements IYandexStationFilter {
    @Nullable
    private UUIDCriteria id;
    @Nullable
    private StringCriteria title;
    @Nullable
    private CodeFilter code;
    @Nullable
    private SettlementFilter settlement;
}
