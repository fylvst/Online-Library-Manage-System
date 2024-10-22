package com.library.service.exception;

/**
 * @Author: zbq
 * @Date: 2023/4/9 下午9:33
 * 密码错误异常
 */
public class WrongPasswdException extends ServiceException{
    public WrongPasswdException() {
        super();
    }

    public WrongPasswdException(String message) {
        super(message);
    }

    public WrongPasswdException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongPasswdException(Throwable cause) {
        super(cause);
    }

    protected WrongPasswdException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
