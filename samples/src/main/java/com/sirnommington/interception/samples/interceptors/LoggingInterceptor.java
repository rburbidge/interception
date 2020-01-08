package com.sirnommington.interception.samples.interceptors;

import com.sirnommington.interception.Interceptor;
import com.sirnommington.interception.Operation;

public class LoggingInterceptor implements Interceptor {
    public Object execute(Operation operation) {
        System.out.println("Begin operation " + operation.name() + " with input " + operation.getInput());
        Object result = operation.execute();
        System.out.println("End operation " + operation.name() + " with result " + result);
        return result;
    }
}
