package com.minakov.yandexraspintegration.controller.graphql.input.settlement;

import com.minakov.yandexraspintegration.controller.graphql.input.code.CodeFilter;
import com.minakov.yandexraspintegration.controller.graphql.input.filter.IYandexRaspFilter;
import com.minakov.yandexraspintegration.controller.graphql.input.filter.StringCriteria;
import com.minakov.yandexraspintegration.controller.graphql.input.filter.UUIDCriteria;
import com.minakov.yandexraspintegration.controller.graphql.input.region.RegionFilter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SettlementFilter implements IYandexRaspFilter {
    @Nullable
    private UUIDCriteria id;
    @Nullable
    private StringCriteria title;
    @Nullable
    private CodeFilter code;
    @Nullable
    private RegionFilter region;
}
