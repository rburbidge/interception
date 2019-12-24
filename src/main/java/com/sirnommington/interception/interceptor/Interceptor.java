package com.sirnommington.interception.interceptor;

/**
 * An interceptor.
 * See {@link com.sirnommington.interception.sample} for samples.
 */
public interface Interceptor {
    /**
     * Executes some logic surrounding {@link ContinuableOperation#execute()}.
     * @param operation The operation.
     * @return The result of {@link ContinuableOperation#execute()}, or the result of the interceptor.
     */
    Object execute(ContinuableOperation operation);
}
