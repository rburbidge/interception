package com.sirnommington.interception.sample.code;

import com.sirnommington.interception.InterceptorChain;
import com.sirnommington.interception.InterceptorContext;
import com.sirnommington.interception.sample.LogBeginEndInterceptor;

public class InterceptorChainSampleCode {

    // TODO Add more interceptors to the default chain
    private final InterceptorChain chain = InterceptorChain.builder()
            .interceptor(new LogBeginEndInterceptor())
            .build();

    public Object withOutput() {
        return chain.execute("getUsers", () -> {
            return null;
        });
    }

    public Integer withTypedOutput() {
        return chain.<Integer>execute("getUsers", () -> {
            return new Integer(1337);
        });
    }

    public Object withInputAndOutput() {
        return chain.execute("getUsers", (input) -> {
            return null;
        });
    }

    // TODO With void return
    // TODO With void return and no input
    // TODO With more context parameters (different between two methods)
}
