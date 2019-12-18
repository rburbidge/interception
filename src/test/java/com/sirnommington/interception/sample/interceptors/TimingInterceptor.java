package com.sirnommington.interception.sample.interceptors;

import com.sirnommington.interception.Interceptor;
import com.sirnommington.interception.Operation;

import java.util.function.Function;

public class TimingInterceptor implements Interceptor {
    @Override
    public <T, R> R execute(Operation<T> context, Function<T, R> operation) {
        long startTime = System.nanoTime();

        try {
            return context.execute(operation);
        } finally {
            long durationMs = (System.nanoTime() - startTime) / 1000000;
            System.out.println(context.operationName() + " execution time: " + durationMs + "ms");
        }
    }
}
