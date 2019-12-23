package com.sirnommington.interception.sample;

import com.sirnommington.interception.Interceptor;
import com.sirnommington.interception.Operation;
import com.sirnommington.interception.sample.interceptors.authretry.AuthRetryInterceptor;
import com.sirnommington.interception.sample.interceptors.LoggingInterceptor;
import com.sirnommington.interception.sample.interceptors.TimingInterceptor;

import java.util.LinkedList;
import java.util.List;

public class SampleCode {

    private static final List<Interceptor> chain = new LinkedList<>();
    static {
        chain.add(new AuthRetryInterceptor(null));
        chain.add(new LoggingInterceptor());
        chain.add(new TimingInterceptor());
    }

    public void test() {
        String functionResult =  Operation.builder()
                .operationName("functionOperation")
                .build()
                .execute("functional input", (theInput) -> "consume input to produce output");

        String supplierResult = Operation.builder()
                .build()
                .execute(() -> "supplier output");

        Operation.builder()
                .build()
                .execute(() -> { /* run some things */ });

        Operation.builder()
                .build()
                .execute("consumer input", (input) -> { /* consume the input */ });
    }
}
