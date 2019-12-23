package com.sirnommington.interception.sample.interceptors.authretry;

import com.sirnommington.interception.InterceptorChain;
import com.sirnommington.interception.sample.interceptors.authretry.AuthRetryInterceptor;
import com.sirnommington.interception.sample.interceptors.authretry.AuthenticatedRequest;
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

    @Before
    public void before() {
        interceptor = new AuthRetryInterceptor(tokenProvider);
    }

    @Test
    public void execute_successWithCachedToken_setsCachedTokenOnRequest() {
        when(tokenProvider.getCachedToken()).thenReturn("cachedToken");
        when(operation.apply(any())).thenReturn("result");

        String result = new InterceptorChain()
                .interceptor(interceptor)
                .operation("doOperation")
                .input(request)
                .execute(operation);

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

        String result = new InterceptorChain()
                .interceptor(interceptor)
                .operation("doOperation")
                .input(request)
                .execute(operation);

        verify(tokenProvider).getCachedToken();
        verify(request).setToken("cachedToken");
        verify(tokenProvider).refreshToken();
        verify(request).setToken("refreshedToken");
        verify(operation, times(2)).apply(request);
        assertEquals("result", result);
    }
}
