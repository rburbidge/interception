package com.sirnommington.interception.core;

import com.sirnommington.interception.ExecutableOperation;
import com.sirnommington.interception.interceptor.Interceptor;
import com.sirnommington.interception.interceptor.ContinuableOperation;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Accessors(fluent = true)
public class Operation implements ContinuableOperation, ExecutableOperation {
    private final Iterator<Interceptor> interceptors;

    private Map<Object, Object> params = new HashMap<>();

    /**
     * Any input for the operation. Will be provided to func.
     */
    private Object input;

    /**
     * The task to run. If a runnable, consumer, or supplier was executed, then it will be wrapped in a function and
     * assigned here.
     */
    private Function<Object, Object> func;

    public Operation(Iterator<Interceptor> interceptors) {
        this.interceptors = interceptors;
    }

    ///////////////////////////
    // OperationBase methods //
    ///////////////////////////

    @Override
    public ExecutableOperation param(Object key, Object value) {
        params.put(key, value);
        return this;
    }

    @Override
    public Object param(Object key) {
        return params.get(key);
    }

    /////////////////////////////////////////
    // InterceptorOperationContext methods //
    /////////////////////////////////////////

    @Override
    public Object getInput() {
        return input;
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

        return (R) execute();
    }
}