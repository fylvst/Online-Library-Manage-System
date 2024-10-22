package com.library.service.exception;

/**
 * @Author: zbq
 * @Date: 2023/5/8 上午11:12
 */
public class UpdateDataBaseException extends ServiceException{
    public UpdateDataBaseException() {
        super();
    }

    public UpdateDataBaseException(String message) {
        super(message);
    }

    public UpdateDataBaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public UpdateDataBaseException(Throwable cause) {
        super(cause);
    }

    protected UpdateDataBaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
