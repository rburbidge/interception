package com.sirnommington.interception;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public interface ExecutableOperation {
    ExecutableOperation operationName(String operationName);

    <T, R> R execute(T input, Function<T, R> func);

    <R> R execute(Supplier<R> func);

    <T> void execute(T input, Consumer<T> func);

    void execute(Runnable func);
}
