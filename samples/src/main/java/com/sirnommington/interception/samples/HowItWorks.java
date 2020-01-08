package com.sirnommington.interception.samples;

import com.sirnommington.interception.Interceptor;
import com.sirnommington.interception.InterceptorChain;
import com.sirnommington.interception.Operation;

/**
 * Example showing how the interceptors will be executed in a series.
 *
 * See the README.md.
 */
public class HowItWorks {

    public static void example() {
        InterceptorChain chain = InterceptorChain.builder()
                .interceptor(new EnterExitInterceptor("A"))
                .interceptor(new EnterExitInterceptor("B"))
                .interceptor(new EnterExitInterceptor("C"))
                .build();

        chain.start().execute(() -> { System.out.println("Do some work"); });
    }

    /**
     * Sample interceptor the logs enter/exit and its name.
     */
    private static class EnterExitInterceptor implements Interceptor {
        private final String name;

        public EnterExitInterceptor(String name) { this.name = name; }

        public Object execute(Operation operation) {
            System.out.println("Enter " + this.name);
            Object result = operation.execute();
            System.out.println("Exit " + this.name);
            return result;
        }
    }
}
