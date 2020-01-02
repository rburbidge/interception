package com.sirnommington.interception.interceptor;

/**
 * An interceptor.
 * See {@link com.sirnommington.interception.sample} for samples.
 */
public interface Interceptor {
    /**
     * Executes some logic surrounding {@link Operation#execute()}.
     * @param operation The operation.
     * @return The result of {@link Operation#execute()}, or the result of the interceptor.
     */
    Object execute(Operation operation);
}
