package com.sirnommington.interception.samples;

import com.sirnommington.interception.InterceptorChain;
import com.sirnommington.interception.samples.interceptors.authretry.AuthRetryInterceptor;
import com.sirnommington.interception.samples.interceptors.LoggingInterceptor;
import com.sirnommington.interception.samples.interceptors.TimingInterceptor;

public class SampleCode {

    private static final InterceptorChain pipeline = InterceptorChain.builder()
            .interceptor(new AuthRetryInterceptor(null))
            .interceptor(new LoggingInterceptor())
            .interceptor(new TimingInterceptor())
            .build();

    public void test() {
        String functionResult =  pipeline.start()
                .name("functionOperation")
                .execute("functional input", (theInput) -> "consume input to produce output");

        String supplierResult = pipeline.start()
                .name("supplierOperation")
                .execute(() -> "supplier output");

        pipeline.start()
                .name("runnableOperation")
                .execute(() -> { /* run some things */ });

        pipeline.start()
                .name("consumerOperation")
                .execute("consumer input", (input) -> { /* consume the input */ });
    }
}
