package com.sirnommington.interception.sample.interceptors;

import com.sirnommington.interception.interceptor.Interceptor;
import com.sirnommington.interception.interceptor.Operation;

public class TimingInterceptor implements Interceptor {
    @Override
    public Object execute(Operation operation) {
        long startTime = System.nanoTime();

        try {
            return operation.execute();
        } finally {
            long durationMs = (System.nanoTime() - startTime) / 1000000;
            System.out.println(operation.name() + " execution time: " + durationMs + "ms");
        }
    }
}
