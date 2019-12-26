package com.sirnommington.interception.sample.interceptors;

import com.sirnommington.interception.interceptor.Interceptor;
import com.sirnommington.interception.interceptor.ContinuableOperation;

public class LoggingInterceptor implements Interceptor {
    @Override
    public Object execute(ContinuableOperation operation) {
        String operationName = (String) operation.param(Params.OPERATION_NAME);
        System.out.println("Begin operation " + operationName + " with input " + operation.getInput());
        Object result = operation.execute();
        System.out.println("End operation " + operationName + " with result " + result);
        return result;
    }
}
