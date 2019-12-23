package com.sirnommington.interception;

public interface InterceptorOperationContext {
    String getOperationName();
    Object getInput();
    Object execute();
}
