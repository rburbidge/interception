package com.sirnommington.interception.sample.interceptors;

import com.sirnommington.interception.interceptor.Interceptor;
import com.sirnommington.interception.interceptor.Operation;

public class LoggingInterceptor implements Interceptor {
    @Override
    public Object execute(Operation operation) {
        System.out.println("Begin operation " + operation.name() + " with input " + operation.getInput());
        Object result = operation.execute();
        System.out.println("End operation " + operation.name() + " with result " + result);
        return result;
    }
}
