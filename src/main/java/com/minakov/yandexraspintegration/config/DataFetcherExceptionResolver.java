package com.minakov.yandexraspintegration.config;

import graphql.GraphQLError;
import graphql.schema.DataFetchingEnvironment;
import java.util.List;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class DataFetcherExceptionResolver extends DataFetcherExceptionResolverAdapter {
    @Nullable
    @Override
    protected List<GraphQLError> resolveToMultipleErrors(Throwable ex, DataFetchingEnvironment env) {
        ex.printStackTrace();
        return super.resolveToMultipleErrors(ex, env);
    }

    @Nullable
    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        ex.printStackTrace();
        return super.resolveToSingleError(ex, env);
    }
}
