package com.sirnommington.interception.samples.interceptors.authretry;

public interface AuthenticatedRequest {
    void setToken(String token);
}
