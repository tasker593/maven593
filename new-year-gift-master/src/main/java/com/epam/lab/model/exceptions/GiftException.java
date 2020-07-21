package com.epam.lab.model.exceptions;

public class GiftException extends Exception {

    private static final long serialVersionUID = 1L;

    public GiftException() {
    }

    public GiftException(String message) {
        super(message);
    }

    public GiftException(String message, Throwable cause) {
        super(message, cause);
    }

    // addition exceptions from superclass
    public GiftException(Throwable cause) {
        super(cause);
    }

    public GiftException(String message, Throwable cause,
                         boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
