package com.sirnommington.interception;

import java.util.LinkedList;
import java.util.List;

/**
 * A reusable chain of interceptors.
 */
public class InterceptorChain {
    private final List<Interceptor> interceptors = new LinkedList<>();

    /**
     * Add an interceptor to the end of the chain.
     * @param interceptor The interceptor to add.
     * @return this.
     */
    public InterceptorChain interceptor(Interceptor interceptor) {
        this.interceptors.add(interceptor);
        return this;
    }

    /**
     * Initializes an executable operation for this chain.
     * @param operationName The operation name.
     * @return the operation.
     * @throws UnsupportedOperationException if no interceptors have been added.
     */
    public Operation<?> operation(String operationName) {
        if(interceptors.isEmpty()) {
            throw new UnsupportedOperationException("Must have at least one interceptor");
        }

        return new Operation()
                .interceptors(interceptors.iterator())
                .operationName(operationName);
    }
}
