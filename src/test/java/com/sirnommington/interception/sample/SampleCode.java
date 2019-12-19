package com.sirnommington.interception.sample;

import com.sirnommington.interception.InterceptorChain;
import com.sirnommington.interception.sample.interceptors.AuthRetryInterceptor;
import com.sirnommington.interception.sample.interceptors.LoggingInterceptor;
import com.sirnommington.interception.sample.interceptors.TimingInterceptor;

public class SampleCode {

    private final InterceptorChain chain = new InterceptorChain()
            .interceptor(new AuthRetryInterceptor((AuthRetryInterceptor.TokenProvider) null))
            .interceptor(new LoggingInterceptor())
            .interceptor(new TimingInterceptor());

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
