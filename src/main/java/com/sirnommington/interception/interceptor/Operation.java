package com.sirnommington.interception.interceptor;

/**
 * An operation from the perspective of an interceptor.
 */
public interface Operation {
    /**
     * Gets the operation name.
     * @return The operation name.
     */
    String operationName();

    /**
     * Gets a parameter.
     * @param key The parameter key.
     * @return The value.
     */
    Object param(Object key);

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
