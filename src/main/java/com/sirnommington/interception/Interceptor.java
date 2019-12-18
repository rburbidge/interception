package com.sirnommington.interception;

import java.util.function.Function;

public interface Interceptor {
    <T, R> R execute(Operation<T> context, Function<T, R> operation);
}
