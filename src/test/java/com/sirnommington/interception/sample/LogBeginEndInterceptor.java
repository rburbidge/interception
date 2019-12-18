package com.sirnommington.interception.sample;

import com.sirnommington.interception.Interceptor;
import com.sirnommington.interception.Operation;

import java.util.function.Function;

public class LogBeginEndInterceptor implements Interceptor {
    @Override
    public <T, R> R execute(Operation<T> context, Function<T, R> operation) {
        System.out.println("Begin operation " + context.operationName());
        R result = context.execute(operation);
        System.out.println("End operation " + context.operationName());
        return result;
    }
}
