package com.sirnommington.interception.interceptor;

/**
 * An operation from the perspective of an interceptor.
 */
public interface ContinuableOperation {
    /**
     * The operation name.
     * @return The operation name.
     */
    String getOperationName();

    /**
     * The operation input. May be null.
     * @return The operation input.
     */
    Object getInput();

    /**
     * Continues execution of the operation.
     * @return The operation result. May be null.
     */
    Object execute();
}
