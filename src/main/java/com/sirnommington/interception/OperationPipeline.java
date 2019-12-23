package com.sirnommington.interception;

import lombok.Builder;
import lombok.Singular;

import java.util.List;

@Builder
public class OperationPipeline {
    @Singular
    private List<Interceptor> interceptors;

    public ExecutableOperation start() {
        return new Operation(interceptors.iterator());
    }
}
