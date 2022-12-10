package com.example.todayclient.repository;

import static com.example.todayclient.repository.Status.ERROR;
import static com.example.todayclient.repository.Status.LOADING;
import static com.example.todayclient.repository.Status.SUCCESS;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

public class Resource<T> {

    @NonNull
    private final Status _status;

    @Nullable
    private final String _message;

    @Nullable
    private final T _data;

    public Resource(@NonNull Status status, @Nullable T data, @Nullable String message) {
        this._status = status;
        this._data = data;
        this._message = message;
    }

    public static <T> Resource<T> success(@Nullable T data) {
        return new Resource<>(SUCCESS, data, null);
    }

    public static <T> Resource<T> error(String msg, @Nullable T data) {
        return new Resource<>(ERROR, data, msg);
    }

    public static <T> Resource<T> loading(@Nullable T data) {
        return new Resource<>(LOADING, data, null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Resource<?> resource = (Resource<?>) o;

        if (_status != resource.getStatus()) {
            return false;
        }
        if (!Objects.equals(_message, resource.getMessage())) {
            return false;
        }
        return Objects.equals(_data, resource.getData());
    }

    public T getData() {
        return _data;
    }

    public Status getStatus() {
        return _status;
    }

    public String getMessage() {
        return _message;
    }


    @Override
    public int hashCode() {
        int result = _status.hashCode();
        result = 31 * result + (_message != null ? _message.hashCode() : 0);
        result = 31 * result + (_data != null ? _data.hashCode() : 0);
        return result;
    }

    @NonNull
    @Override
    public String toString() {
        return "Resource{" +
                "status=" + _status +
                ", message='" + _message + '\'' +
                ", data=" + _data +
                '}';
    }
}
