package com.sirnommington.interception;

import java.util.function.Supplier;

public interface Interceptor {
    <T> T execute(InterceptorContext context, Supplier<T> operation);
}
