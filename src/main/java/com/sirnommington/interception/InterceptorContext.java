package com.sirnommington.interception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Iterator;
import java.util.function.Function;

@Accessors(fluent = true)
@NoArgsConstructor
public class InterceptorContext<T> {
    @Getter
    @Setter
    private String operationName;

    @Getter
    @Setter
    private T input;

    @Setter
    private Iterator<Interceptor> interceptors;

    public <R> R proceed(Function<T, R> operation) {
        if(interceptors.hasNext()) {
            Interceptor cur = interceptors.next();
            return cur.execute(this, operation);
        }

        return operation.apply(this.input);
    }
}
