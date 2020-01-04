package com.sirnommington.interception.sample.interceptors.authretry;

import com.sirnommington.interception.InterceptorChain;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.function.Function;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AuthRetryInterceptorTest {

    @Mock private AuthRetryInterceptor.TokenProvider tokenProvider;
    @Mock private Function<AuthenticatedRequest, String> operation;
    @Mock private AuthenticatedRequest request;

    private AuthRetryInterceptor interceptor;
    private InterceptorChain pipeline;

    @Before
    public void before() {
        interceptor = new AuthRetryInterceptor(tokenProvider);
        pipeline = InterceptorChain.builder()
                .interceptor(interceptor)
                .build();
    }

    @Test
    public void execute_successWithCachedToken_setsCachedTokenOnRequest() {
        when(tokenProvider.getCachedToken()).thenReturn("cachedToken");
        when(operation.apply(any())).thenReturn("result");

        String result = pipeline.start()
                .name("doOperation")
                .execute(request, operation);

        verify(tokenProvider).getCachedToken();
        verify(request).setToken("cachedToken");
        verify(operation).apply(request);
        assertEquals("result", result);
    }

    @Test
    public void execute_successWithRefreshedToken_setsRefreshedTokenOnRequest() {
        when(tokenProvider.getCachedToken()).thenReturn("cachedToken");
        when(tokenProvider.refreshToken()).thenReturn("refreshedToken");
        when(operation.apply(any()))
                .thenThrow(new AuthRetryInterceptor.AuthenticationException())
                .thenReturn("result");

        String result = pipeline.start()
                .name("doOperation")
                .execute(request, operation);

        verify(tokenProvider).getCachedToken();
        verify(request).setToken("cachedToken");
        verify(tokenProvider).refreshToken();
        verify(request).setToken("refreshedToken");
        verify(operation, times(2)).apply(request);
        assertEquals("result", result);
    }
}
