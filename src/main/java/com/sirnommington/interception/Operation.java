package com.sirnommington.interception;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Operation implements InterceptorOperationContext {
    private final Iterator<Interceptor> interceptors;

    @Getter
    private final String operationName;

    @Getter
    private Object input;

    private Function<Object, Object> func;

    private Operation(Iterator<Interceptor> interceptors, String operationName) {
        this.interceptors = interceptors;
        this.operationName = operationName;
    }

    public <T, R> R execute(T input, Function<T, R> func) {
        return this.executeImpl(input, func);
    }

    public <R> R execute(Supplier<R> func) {
        return this.executeImpl(null, (unused) -> func.get());
    }

    public <T> void execute(T input, Consumer<T> func) {
        this.executeImpl(input, (theInput) -> {
            func.accept(theInput);
            return null;
        });
    }

    public void execute(Runnable func) {
        this.executeImpl(null, (unused) -> {
            func.run();
            return null;
        });
    }

    private  <T, R> R executeImpl(T input, Function<T, R> func) {
        this.input = input;
        this.func = (Object objInput) -> {
            return func.apply((T) objInput);
        };

        return execute();
    }

    public <R> R execute() {
        if(interceptors.hasNext()) {
            Interceptor cur = interceptors.next();
            return (R) cur.execute(this);
        }

        return (R) func.apply(input);
    }

    public static Builder builder() {
        return new Builder();
    }

    @Accessors(fluent = true)
    @Setter
    public static class Builder {
        private Iterator<Interceptor> interceptors;
        private String operationName;
        public Operation build() {
            return new Operation(interceptors, operationName);
        }
    }
}
