package com.sirnommington.interception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Accessors(fluent = true)
@NoArgsConstructor
public class Operation<T> {
    @Getter
    @Setter
    private String operationName;

    @Getter
    private T input;

    public <R> Operation<R> input(R input) {
        this.input = (T)input;
        return (Operation<R>)this;
    }

    @Setter
    private Iterator<Interceptor> interceptors;

    public <R> R execute(Function<T, R> operation) {
       return executeImpl(operation);
    }

    public <R> R execute(Supplier<R> operation) {
        return executeImpl(
                (unused) -> operation.get());
    }

    public void execute(Consumer<T> operation) {
        this.executeImpl((input) -> {
            operation.accept(input);
            return null;
        });
    }

    public void execute(Runnable operation) {
        this.executeImpl((unused) -> {
            operation.run();
            return null;
        });
    }

    private <R> R executeImpl(Function<T, R> operation) {
        if(interceptors.hasNext()) {
            Interceptor<T> cur = interceptors.next();
            return cur.execute(this, operation);
        }

        return operation.apply(this.input);
    }
}
