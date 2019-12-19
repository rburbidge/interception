package com.sirnommington.interception.sample.interceptors;

import com.sirnommington.interception.Interceptor;
import com.sirnommington.interception.Operation;

import java.util.function.Function;

/**
 * Sets an authentication token on the request, and retries with a new token if the initial request fails.
 */
public class AuthRetryInterceptor implements Interceptor<AuthRetryInterceptor.AuthenticatedRequest> {

    private final TokenProvider tokenProvider;

    public AuthRetryInterceptor(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public <R> R execute(
            Operation<AuthRetryInterceptor.AuthenticatedRequest> context,
            Function<AuthRetryInterceptor.AuthenticatedRequest, R> operation) {
        try {
            // Try using the cached token
            context.input().setToken(tokenProvider.getCachedToken());
            return context.execute(operation);
        } catch(AuthenticationException e) {
            // If that fails, then try using a new token
            context.input().setToken(tokenProvider.refreshToken());
            return context.execute(operation);
        }
    }

    public interface AuthenticatedRequest {
        void setToken(String token);
    }

    public static class AuthenticationException extends RuntimeException {
    }

    public interface TokenProvider {
        String getCachedToken();

        String refreshToken();
    }
}
