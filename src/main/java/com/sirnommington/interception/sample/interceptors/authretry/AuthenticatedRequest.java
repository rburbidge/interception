package com.sirnommington.interception.sample.interceptors.authretry;

public interface AuthenticatedRequest {
    void setToken(String token);
}
