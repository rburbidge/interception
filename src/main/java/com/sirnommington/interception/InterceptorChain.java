package com.sirnommington.interception;

import com.sirnommington.interception.core.OperationImpl;
import lombok.Builder;
import lombok.Singular;

import java.util.List;

@Builder
public class InterceptorChain {
    @Singular
    private List<Interceptor> interceptors;

    public ExecutableOperation start() {
        return new OperationImpl(interceptors.iterator());
    }
}
