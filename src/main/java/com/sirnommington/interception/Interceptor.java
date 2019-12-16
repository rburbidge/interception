package com.sirnommington.interception;

import java.util.function.Function;

public interface Interceptor {
    <T, R> R execute(InterceptorContext<T> context, Function<T, R> operation);
}
