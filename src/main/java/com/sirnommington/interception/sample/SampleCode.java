package com.sirnommington.interception.sample;

import com.sirnommington.interception.OperationPipeline;
import com.sirnommington.interception.sample.interceptors.authretry.AuthRetryInterceptor;
import com.sirnommington.interception.sample.interceptors.LoggingInterceptor;
import com.sirnommington.interception.sample.interceptors.TimingInterceptor;

public class SampleCode {

    private static final OperationPipeline pipeline = OperationPipeline.builder()
            .interceptor(new AuthRetryInterceptor(null))
            .interceptor(new LoggingInterceptor())
            .interceptor(new TimingInterceptor())
            .build();

    public void test() {
        String functionResult =  pipeline.start()
                .operationName("functionOperation")
                .execute("functional input", (theInput) -> "consume input to produce output");

        String supplierResult = pipeline.start()
                .operationName("supplierOperation")
                .execute(() -> "supplier output");

        pipeline.start()
                .operationName("runnableOperation")
                .execute(() -> { /* run some things */ });

        pipeline.start()
                .operationName("consumerOperation")
                .execute("consumer input", (input) -> { /* consume the input */ });
    }
}
