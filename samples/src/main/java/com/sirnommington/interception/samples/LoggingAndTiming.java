package com.sirnommington.interception.samples;

import com.sirnommington.interception.InterceptorChain;
import com.sirnommington.interception.samples.interceptors.LoggingInterceptor;
import com.sirnommington.interception.samples.interceptors.TimingInterceptor;

public class LoggingAndTiming {
    public static void example() {
        InterceptorChain chain = InterceptorChain.builder()
                .interceptor(new LoggingInterceptor())
                .interceptor(new TimingInterceptor())
                .build();

        int multiplyResult = chain.start()
                .name("multiplyBy2")
                .execute(2, num -> num * 2);

        int divideResult = chain.start()
                .name("divideBy2")
                .execute(2, num -> num / 2);
    }
}
