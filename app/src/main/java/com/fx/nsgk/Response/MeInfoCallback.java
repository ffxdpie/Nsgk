package com.fx.nsgk.Response;

// MeInfoCallback.java

public interface MeInfoCallback {
    void onSuccess(UserResponse userResponse);
    void onFailure(Throwable t);
}

