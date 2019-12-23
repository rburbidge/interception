package com.sirnommington.interception;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.function.Function;
import java.util.function.Supplier;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class InterceptorChainTest {

    private Interceptor interceptor = Mockito.spy(new Interceptor<Object>() {
        @Override
        public <R> R execute(Operation<Object> context, Function<Object, R> operation) {
            return context.execute(operation);
        }
    });

    @Test(expected = UnsupportedOperationException.class)
    public void supplier_noInterceptors() {
        Supplier operation = Mockito.mock(Supplier.class);

        InterceptorChain chain = new InterceptorChain();

        try {
            chain.operation("operationName").execute(operation);
        } finally {
            verifyZeroInteractions(operation);
            verify(operation, never()).get();
        }
    }

    @Test
    public void function_singleInterceptor() {
        String expected = "The Result";
        Function<Void, String> operation = (v) -> {
            return expected;
        };

        InterceptorChain chain = new InterceptorChain()
                .interceptor(interceptor);
        Operation<Void> operationContext = (Operation<Void>)chain.operation("operationName");
        String actual = operationContext.execute(operation);

        verify(interceptor).execute(operationContext, operation);
        assertEquals(expected, actual);
    }

    @Test
    public void function_multiInterceptor() {
        String expected = "The Result";
        Function<String, String> operation = (v) -> {
            return expected;
        };

        InterceptorChain chain = new InterceptorChain()
                .interceptor(interceptor)
                .interceptor(interceptor)
                .interceptor(interceptor);
        Operation<String> operationContext = (Operation<String>)chain.operation("operationName");
        String actual = operationContext.execute(operation);

        verify(interceptor, times(3)).execute(operationContext, operation);
        assertEquals(expected, actual);
    }

    @Test
    public void supplier_singleInterceptor() {
        String expected = "The Result";
        Supplier<String> operation = () -> {
            return expected;
        };

        InterceptorChain chain = new InterceptorChain()
                .interceptor(interceptor);
        Operation<Void> operationContext = (Operation<Void>)chain.operation("operationName");
        String actual = operationContext.execute(operation);

        verify(interceptor).execute(any(), any());
        assertEquals(expected, actual);
    }
}
