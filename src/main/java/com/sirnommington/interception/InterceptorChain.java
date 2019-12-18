package com.sirnommington.interception;

import lombok.Builder;
import lombok.Singular;
import lombok.experimental.Accessors;

import java.util.List;

@Accessors(fluent = true)
@Builder
public class InterceptorChain {
    @Singular
    private final List<Interceptor> interceptors;

    public Operation<?> operation(String operationName) {
        return new Operation()
                .interceptors(interceptors.iterator())
                .operationName(operationName);
    }
}
