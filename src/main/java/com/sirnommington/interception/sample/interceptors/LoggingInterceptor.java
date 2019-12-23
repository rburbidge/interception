package com.sirnommington.interception.sample.interceptors;

import com.sirnommington.interception.interceptor.Interceptor;
import com.sirnommington.interception.interceptor.ContinuableOperation;

public class LoggingInterceptor implements Interceptor {
    @Override
    public Object execute(ContinuableOperation operation) {
        System.out.println("Begin operation " + operation.getOperationName() + " with input " + operation.getInput());
        Object result = operation.execute();
        System.out.println("End operation " + operation.getOperationName() + " with result " + result);
        return result;
    }
}
