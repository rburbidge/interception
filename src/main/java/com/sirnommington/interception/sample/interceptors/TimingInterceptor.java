package com.sirnommington.interception.sample.interceptors;

import com.sirnommington.interception.interceptor.Interceptor;
import com.sirnommington.interception.interceptor.Operation;

public class TimingInterceptor implements Interceptor {
    @Override
    public Object execute(Operation operation) {
        String operationName = (String) operation.param(Params.OPERATION_NAME);
        long startTime = System.nanoTime();

        try {
            return operation.execute();
        } finally {
            long durationMs = (System.nanoTime() - startTime) / 1000000;
            System.out.println(operationName + " execution time: " + durationMs + "ms");
        }
    }
}
