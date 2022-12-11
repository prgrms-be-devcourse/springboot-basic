package org.prgrms.kdt.exception.repository;

import java.io.IOException;

public class NotPresentInRepositoryException extends RuntimeException {
    public NotPresentInRepositoryException(String message) {
        super(message);
    }

    public NotPresentInRepositoryException(String message, IOException e) {
        super(message, e);
    }
}
