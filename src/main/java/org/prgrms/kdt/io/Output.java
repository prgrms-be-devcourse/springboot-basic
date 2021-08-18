package org.prgrms.kdt.io;

import org.prgrms.kdt.exception.InvalidIOMessageException;

public interface Output {
    void write(String message) throws InvalidIOMessageException;
}
