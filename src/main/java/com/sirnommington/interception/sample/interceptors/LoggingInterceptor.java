package com.sirnommington.interception.sample.interceptors;

import com.sirnommington.interception.Interceptor;
import com.sirnommington.interception.Operation;

import java.util.function.Function;

public class LoggingInterceptor implements Interceptor<Object> {
    @Override
    public <R> R execute(Operation<Object> context, Function<Object, R> operation) {
        System.out.println("Begin operation " + context.operationName() + " with input " + context.input());
        R result = context.execute(operation);
        System.out.println("End operation " + context.operationName() + " with result " + result);
        return result;
    }
}
