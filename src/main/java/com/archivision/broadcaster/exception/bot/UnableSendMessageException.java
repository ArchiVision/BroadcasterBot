package com.archivision.broadcaster.exception.bot;

public class UnableSendMessageException extends RuntimeException {
    public UnableSendMessageException(String message, Throwable cause) {
        super(message, cause);
    }
}
