package com.sirnommington.interception.sample.interceptors;

import com.sirnommington.interception.Interceptor;
import com.sirnommington.interception.InterceptorOperationContext;

public class TimingInterceptor implements Interceptor {
    @Override
    public Object execute(InterceptorOperationContext operation) {
        long startTime = System.nanoTime();

        try {
            return operation.execute();
        } finally {
            long durationMs = (System.nanoTime() - startTime) / 1000000;
            System.out.println(operation.getOperationName() + " execution time: " + durationMs + "ms");
        }
    }
}
