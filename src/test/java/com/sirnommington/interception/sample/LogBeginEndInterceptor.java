package com.sirnommington.interception.sample;

import com.sirnommington.interception.Interceptor;
import com.sirnommington.interception.InterceptorContext;

import java.util.function.Function;

public class LogBeginEndInterceptor implements Interceptor {
    @Override
    public <T, R> R execute(InterceptorContext<T> context, Function<T, R> operation) {
        System.out.println("Begin operation " + context.operationName());
        R result = context.proceed(operation);
        System.out.println("End operation " + context.operationName());
        return result;
    }
}
