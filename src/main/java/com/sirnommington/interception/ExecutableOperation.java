package com.sirnommington.interception;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Configure an operation and then execute it.
 */
public interface ExecutableOperation {
    /**
     * Gets the operation name.
     * @param name The operation name.
     */
    void operationName(String name);

    /**
     * Sets a parameter for the operation.
     * @param key The param key.
     * @param value The param value.
     * @return this.
     */
    ExecutableOperation param(Object key, Object value);

    /**
     * Executes a function.
     * @param input The function input.
     * @param func The function.
     * @param <T> The input type.
     * @param <R> The result type.
     * @return The result.
     */
    <T, R> R execute(T input, Function<T, R> func);

    /**
     * Executes a supplier.
     * @param func The supplier.
     * @param <R> The result type.
     * @return The result.
     */
    <R> R execute(Supplier<R> func);

    /**
     * Executes a consumer.
     * @param input The input.
     * @param func The consumer.
     * @param <T> The input type.
     */
    <T> void execute(T input, Consumer<T> func);

    /**
     * Executes a runnable.
     * @param func The runnable.
     */
    void execute(Runnable func);
}
