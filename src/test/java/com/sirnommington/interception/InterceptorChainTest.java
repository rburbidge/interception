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
        public <T, R> R execute(InterceptorContext<T> context, Function<T, R> operation) {
            return context.proceed(operation);
        }
    });

    @Test
    public void function_noInterceptors() {
        InterceptorContext context = new InterceptorContext();
        String expected = "The Result";
        Supplier<String> operation = () -> {
            return expected;
        };

        InterceptorChain chain = InterceptorChain.builder()
                .build();
        String actual = chain.execute(context, operation);

        assertEquals(expected, actual);
    }

    @Test
    public void function_singleInterceptor() {
        InterceptorContext context = new InterceptorContext();
        String expected = "The Result";
        Function<Void, String> operation = (v) -> {
            return expected;
        };

        InterceptorChain chain = InterceptorChain.builder()
                .interceptor(interceptor)
                .build();
        String actual = chain.execute(context, operation);

        verify(interceptor).execute(context, operation);
        assertEquals(expected, actual);
    }

    @Test
    public void function_multiInterceptor() {
        InterceptorContext context = new InterceptorContext();
        String expected = "The Result";
        Function<Void, String> operation = (v) -> {
            return expected;
        };

        InterceptorChain chain = InterceptorChain.builder()
                .interceptor(interceptor)
                .interceptor(interceptor)
                .interceptor(interceptor)
                .build();
        String actual = chain.execute(context, operation);

        verify(interceptor, times(3)).execute(context, operation);
        assertEquals(expected, actual);
    }

    @Test
    public void supplier_singleInterceptor() {
        InterceptorContext context = new InterceptorContext();
        String expected = "The Result";
        Supplier<String> operation = () -> {
            return expected;
        };

        InterceptorChain chain = InterceptorChain.builder()
                .interceptor(interceptor)
                .build();
        String actual = chain.execute(context, operation);

        verify(interceptor).execute(any(), any());
        assertEquals(expected, actual);
    }
}
