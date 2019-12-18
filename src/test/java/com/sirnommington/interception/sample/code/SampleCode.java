package com.sirnommington.interception.sample.code;

import com.sirnommington.interception.InterceptorChain;
import com.sirnommington.interception.sample.LogBeginEndInterceptor;

public class SampleCode {

    // TODO Add more interceptors to the default chain
    private final InterceptorChain chain = InterceptorChain.builder()
            .interceptor(new LogBeginEndInterceptor())
            .build();

    public Integer withSupplier() {
        return chain.operation("getUsers")
                .execute(() -> new Integer(1337));
    }

    public Integer withFunction() {
        return chain.operation("getUsers")
                .input(new Integer(123))
                .execute((Integer input) -> new Integer(1337));
    }

    public void withRunnable() {
        chain.operation("getUsers")
                .execute(() -> {});
    }

    public void withConsumer() {
        chain.operation("getUsers")
                .input(new Integer(1234))
                .execute((theInput) -> {});
    }
}
