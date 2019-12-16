package com.sirnommington.interception;

import lombok.Builder;
import lombok.Singular;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

@Accessors(fluent = true)
@Builder
public class InterceptorChain {
    @Singular
    private final List<Interceptor> interceptors;

    /**
     * Executes an operation.
     * @param operationName The operation name.
     * @param operation The operation.
     * @param <R> the operation result type.
     * @return The result of the interceptor chain.
     */
    public <R> R execute(String operationName, Supplier<R> operation) {
        return this.execute(
                new InterceptorContext<>()
                        .operationName(operationName),
                (voidz) -> operation.get());
    }

    /**
     * Executes an operation.
     * @param context The context.
     * @param operation The operation.
     * @param <R> the operation result type.
     * @return The result of the interceptor chain.
     */
    public <R> R execute(InterceptorContext<Void> context, Supplier<R> operation) {
        return this.execute(context, (voidz) -> operation.get());
    }

    /**
     * Executes an operation where the input can be modified by the pipeline.
     * @param operationName The operation name.
     * @param operation The operation. Takes {@link InterceptorContext#input()}.
     * @param <T> The operation input type in {@link InterceptorContext#input()}.
     * @param <R> The operation result type.
     * @return The result of the interceptor chain.
     */
    public <T, R> R execute(String operationName, Function<T, R> operation) {
        return this.execute(
                new InterceptorContext<T>()
                        .operationName(operationName),
                operation);
    }

    /**
     * Executes an operation where the input can be modified by the pipeline.
     * @param context The context.
     * @param operation The operation. Takes {@link InterceptorContext#input()}.
     * @param <T> The operation input type in {@link InterceptorContext#input()}.
     * @param <R> The operation result type.
     * @return The result of the interceptor chain.
     */
    public <T, R> R execute(InterceptorContext<T> context, Function<T, R> operation) {
        context.interceptors(interceptors.iterator());
        return context.proceed(operation);
    }
}
