package com.library.service.exception;

/**
 * @Author: zbq
 * @Date: 2023/4/13 下午2:03
 */
public class BookNotAvailException extends ServiceException{
    public BookNotAvailException() {
        super();
    }

    public BookNotAvailException(String message) {
        super(message);
    }

    public BookNotAvailException(String message, Throwable cause) {
        super(message, cause);
    }

    public BookNotAvailException(Throwable cause) {
        super(cause);
    }

    protected BookNotAvailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
