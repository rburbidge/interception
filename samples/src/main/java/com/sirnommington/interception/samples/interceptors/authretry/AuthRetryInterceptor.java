package com.sirnommington.interception.samples.interceptors.authretry;

import com.sirnommington.interception.Interceptor;
import com.sirnommington.interception.Operation;
import lombok.Data;

/**
 * Sets an authentication token on the request, and retries with a new token if the initial request fails.
 */
@Data
public class AuthRetryInterceptor implements Interceptor {

    private final TokenProvider tokenProvider;

    public AuthRetryInterceptor(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public Object execute(Operation operation) {
        AuthenticatedRequest request = (AuthenticatedRequest)operation.getInput();

        try {
            // Try using the cached token
            request.setToken(tokenProvider.getCachedToken());
            return operation.execute();
        } catch(AuthenticationException e) {
            // If that fails, then try using a new token
            request.setToken(tokenProvider.refreshToken());
            return operation.execute();
        }
    }

    public static class AuthenticationException extends RuntimeException {
    }

    public interface TokenProvider {
        String getCachedToken();

        String refreshToken();
    }
}
