package com.sirnommington.interception;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.function.Function;
import java.util.function.Supplier;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class InterceptorChainTest {

    private Interceptor interceptor = Mockito.spy(new Interceptor() {
        @Override
        public <T, R> R execute(Operation<T> context, Function<T, R> operation) {
            return context.execute(operation);
        }
    });

    @Test
    public void function_noInterceptors() {
        String expected = "The Result";
        Supplier<String> operation = () -> {
            return expected;
        };

        InterceptorChain chain = InterceptorChain.builder()
                .build();
        String actual = chain.operation("operationName").execute(() -> operation.get());

        assertEquals(expected, actual);
    }

    @Test
    public void function_singleInterceptor() {
        String expected = "The Result";
        Function<Void, String> operation = (v) -> {
            return expected;
        };

        InterceptorChain chain = InterceptorChain.builder()
                .interceptor(interceptor)
                .build();
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

        InterceptorChain chain = InterceptorChain.builder()
                .interceptor(interceptor)
                .interceptor(interceptor)
                .interceptor(interceptor)
                .build();
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

        InterceptorChain chain = InterceptorChain.builder()
                .interceptor(interceptor)
                .build();
        Operation<Void> operationContext = (Operation<Void>)chain.operation("operationName");
        String actual = operationContext.execute(operation);

        verify(interceptor).execute(any(), any());
        assertEquals(expected, actual);
    }
}
