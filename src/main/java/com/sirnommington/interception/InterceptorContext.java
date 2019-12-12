package com.sirnommington.interception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Iterator;
import java.util.function.Supplier;

@Accessors(fluent = true)
@NoArgsConstructor
public class InterceptorContext {
    @Getter
    @Setter
    private String operationName;

    @Setter
    private Iterator<Interceptor> interceptors;

    public <T> T proceed(Supplier<T> operation) {
        if(interceptors.hasNext()) {
            Interceptor cur = interceptors.next();
            return cur.execute(this, operation);
        }

        return operation.get();
    }
}
