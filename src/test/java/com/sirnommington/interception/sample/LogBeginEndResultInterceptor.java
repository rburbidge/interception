package com.sirnommington.interception.sample;

import com.sirnommington.interception.Interceptor;
import com.sirnommington.interception.InterceptorContext;

import java.util.function.Supplier;

public class LogBeginEndResultInterceptor implements Interceptor {
    @Override
    public <T> T execute(InterceptorContext context, Supplier<T> operation) {
        System.out.println("Begin operation " + context.operationName());
        T result = context.proceed(operation);
        System.out.println("End operation " + context.operationName() + " with result " + result);
        return result;
    }
}
