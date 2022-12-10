package com.example.todayclient.repository;

import android.os.SystemClock;

import java.util.concurrent.TimeUnit;

public class RateLimiter {
    private final long timeout;
    private Long lastFetched;

    public RateLimiter(int timeout, TimeUnit timeUnit) {
        this.timeout = timeUnit.toMillis(timeout);
    }

    public synchronized boolean shouldFetch() {
        long now = now();
        if (lastFetched == null) {
            lastFetched = now;
            return true;
        }
        if (now - lastFetched > timeout) {
            lastFetched = now;
            return true;
        }
        return false;
    }

    private long now() {
        return SystemClock.uptimeMillis();
    }

    public synchronized void reset() {
        lastFetched = null;
    }
}