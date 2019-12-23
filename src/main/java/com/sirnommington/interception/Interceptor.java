package com.sirnommington.interception;

public interface Interceptor {
    Object execute(InterceptorOperationContext operation);
}
