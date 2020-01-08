package com.sirnommington.interception.samples.interceptors;

import com.sirnommington.interception.Interceptor;
import com.sirnommington.interception.Operation;

public class TimingInterceptor implements Interceptor {
    public Object execute(Operation operation) {
        long startTime = System.nanoTime();

        try {
            return operation.execute();
        } finally {
            long durationMs = (System.nanoTime() - startTime);
            System.out.println(operation.name() + " execution time: " + durationMs + "ns");
        }
    }
}
