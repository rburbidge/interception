package com.sirnommington.interception.sample.interceptors;

import com.sirnommington.interception.Interceptor;
import com.sirnommington.interception.Operation;

import java.util.function.Function;

public class TimingInterceptor implements Interceptor<Object> {
    @Override
    public <R> R execute(Operation<Object> context, Function<Object, R> operation) {
        long startTime = System.nanoTime();

        try {
            return context.execute(operation);
        } finally {
            long durationMs = (System.nanoTime() - startTime) / 1000000;
            System.out.println(context.operationName() + " execution time: " + durationMs + "ms");
        }
    }
}
