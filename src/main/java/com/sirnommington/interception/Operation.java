package com.sirnommington.interception;

import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Accessors(fluent = true)
public class Operation implements InterceptorOperationContext, ExecutableOperation {
    private final Iterator<Interceptor> interceptors;

    @Setter
    private String operationName;

    private Object input;
    private Function<Object, Object> func;

    Operation(Iterator<Interceptor> interceptors) {
        this.interceptors = interceptors;
    }

    /////////////////////////////////////////
    // InterceptorOperationContext methods //
    /////////////////////////////////////////

    @Override
    public Object getInput() {
        return input;
    }

    @Override
    public String getOperationName() {
        return operationName;
    }

    @Override
    public Object execute() {
        if(interceptors.hasNext()) {
            Interceptor cur = interceptors.next();
            return cur.execute(this);
        }

        return func.apply(input);
    }

    /////////////////////////////////
    // ExecutableOperation methods //
    /////////////////////////////////

    @Override
    public <T, R> R execute(T input, Function<T, R> func) {
        return this.executeImpl(input, func);
    }

    @Override
    public <R> R execute(Supplier<R> func) {
        return this.executeImpl(null, (unused) -> func.get());
    }

    @Override
    public <T> void execute(T input, Consumer<T> func) {
        this.executeImpl(input, (theInput) -> {
            func.accept(theInput);
            return null;
        });
    }

    @Override
    public void execute(Runnable func) {
        this.executeImpl(null, (unused) -> {
            func.run();
            return null;
        });
    }

    private  <T, R> R executeImpl(T input, Function<T, R> func) {
        this.input = input;
        this.func = (objInput) -> func.apply((T) objInput);

        return (R)execute();
    }
}
