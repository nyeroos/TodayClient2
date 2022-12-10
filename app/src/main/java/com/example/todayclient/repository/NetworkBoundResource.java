package com.example.todayclient.repository;

import android.os.AsyncTask;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.example.todayclient.api.ApiResponse;

import java.util.Objects;


public abstract class NetworkBoundResource<ResultType, RequestType> {
    private final MediatorLiveData<Resource<ResultType>> _result = new MediatorLiveData<>();

    @MainThread
    NetworkBoundResource() {
        _result.setValue(Resource.loading(null));
        LiveData<ResultType> dbSource = loadFromDb();
        _result.addSource(dbSource, data -> {
            _result.removeSource(dbSource);
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource);
            } else {
                _result.addSource(dbSource, newData -> _result.setValue(Resource.success(newData)));
            }
        });
    }

    @MainThread
    private void setValue(Resource<ResultType> newValue) {
        if (!Objects.equals(_result.getValue(), newValue)) {
            _result.setValue(newValue);
        }
    }

    private void fetchFromNetwork(final LiveData<ResultType> dbSource) {
        LiveData<ApiResponse<RequestType>> apiResponse = createCall();
        _result.addSource(dbSource, newData -> setValue(Resource.loading(newData)));
        _result.addSource(apiResponse, response -> {
            _result.removeSource(apiResponse);
            _result.removeSource(dbSource);
            if (response.isSuccessful()) {
                _result.removeSource(dbSource);
                saveResultAndReInit(response.getBody());
            } else {
                onFetchFailed();
                _result.addSource(dbSource,
                        newData -> setValue(Resource.error(response.getErrorMessage(), newData)));
            }
        });
    }

    @MainThread
    private void saveResultAndReInit(RequestType response) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                saveCallResult(response);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                _result.addSource(loadFromDb(), newData -> _result.setValue(Resource.success(newData)));
            }
        }.execute();
    }


    @WorkerThread
    protected abstract void saveCallResult(@NonNull RequestType item);

    @MainThread
    protected boolean shouldFetch(@Nullable ResultType data) {
        return true;
    }

    @NonNull
    @MainThread
    protected abstract LiveData<ResultType> loadFromDb();

    @NonNull
    @MainThread
    protected abstract LiveData<ApiResponse<RequestType>> createCall();

    @MainThread
    protected void onFetchFailed() {
    }

    public final LiveData<Resource<ResultType>> asLiveData() {
        return _result;
    }
}
