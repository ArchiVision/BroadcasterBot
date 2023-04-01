package com.pathz.broadcaster.exception.bot;

public class UnableSendMessageException extends RuntimeException {
    public UnableSendMessageException(String message, Throwable cause) {
        super(message, cause);
    }
}
