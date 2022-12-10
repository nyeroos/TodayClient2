package com.example.todayclient.api;

import androidx.annotation.Nullable;

import java.io.IOException;

import retrofit2.Response;

public class ApiResponse<T> {
    private final int _code;
    @Nullable
    private final T _body;
    @Nullable
    private final String _errorMessage;

    public ApiResponse(Throwable error) {
        _code = 500;
        _body = null;
        _errorMessage = error.getMessage();
    }

    public ApiResponse(Response<T> response) {
        _code = response.code();
        if (response.isSuccessful()) {
            _body = response.body();
            _errorMessage = null;
        } else {
            String message = null;
            if (response.errorBody() != null) {
                try {
                    message = response.errorBody().string();
                } catch (IOException ignored) {
                }
            }
            if (message == null || message.trim().length() == 0) {
                message = response.message();
            }
            _errorMessage = message;
            _body = null;
        }
    }

    public boolean isSuccessful() {
        return _code >= 200 && _code < 300;
    }

    public int getCode() {
        return _code;
    }

    public T getBody() {
        return _body;
    }

    public String getErrorMessage() {
        return _errorMessage;
    }

}
