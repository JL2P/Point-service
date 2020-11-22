package com.point.api.exception;

public class PointExistException extends RuntimeException {
    public PointExistException(String msg, Throwable t) {
        super(msg, t);
    }

    public PointExistException(String msg) {
        super(msg);
    }

    public PointExistException() {
        super();
    }
}
