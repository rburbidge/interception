package com.sirnommington.interception;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.function.Supplier;

@Accessors(fluent = true)
@Builder
public class InterceptorChain {
    @Singular
    private final List<Interceptor> interceptors;

    public <T> T execute(InterceptorContext context, Supplier<T> operation) {
        context.interceptors(interceptors.iterator());
        return context.proceed(operation);
    }

    public <T> T execute(Supplier<T> operation) {
        return this.execute(new InterceptorContext(), operation);
    }
}
