package com.sirnommington.interception.samples;

import com.sirnommington.interception.Interceptor;
import com.sirnommington.interception.InterceptorChain;
import com.sirnommington.interception.Operation;

public class InterceptorParameters {
    public static void example() {
        InterceptorChain chain = InterceptorChain.builder()
                .interceptor(new KetchupInterceptor())
                .build();

        int count = chain.start()
                .name("getKetchup")
                .param("userId", 8317)
                .execute(() -> /* get the ketchup */ 20);

        chain.start()
                .name("addKetchup")
                .param("userId", 2485)
                .execute(() -> /* add the ketchup */ {});
    }

    private static class KetchupInterceptor implements Interceptor {
        @Override
        public Object execute(Operation operation) {
            Integer userId = (Integer) operation.param("userId");

            System.out.println(operation.name() + " called by user " + userId);

            return operation.execute();
        }
    }
}
