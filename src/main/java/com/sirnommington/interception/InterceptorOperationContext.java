package com.sirnommington.interception;

public interface InterceptorOperationContext {
    String getOperationName();
    Object getInput();

    // TODO Ideally this shouldn't have type params at all
    <R> R execute();
}
