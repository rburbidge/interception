package com.sirnommington.interception.sample.interceptors.authretry;

import com.sirnommington.interception.Interceptor;
import com.sirnommington.interception.Operation;

import java.util.function.Function;

/**
 * Sets an authentication token on the request, and retries with a new token if the initial request fails.
 */
public class AuthRetryInterceptor implements Interceptor<AuthenticatedRequest> {

    private final TokenProvider tokenProvider;

    public AuthRetryInterceptor(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public <R> R execute(
            Operation<AuthenticatedRequest> context,
            Function<AuthenticatedRequest, R> operation) {
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

    public static class AuthenticationException extends RuntimeException {
    }

    public interface TokenProvider {
        String getCachedToken();

        String refreshToken();
    }
}
