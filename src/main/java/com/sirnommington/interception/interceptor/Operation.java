package com.sirnommington.interception.interceptor;

/**
 * An operation from the perspective of an interceptor.
 */
public interface Operation {
    Object param(Object key);

    String operationName();

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
