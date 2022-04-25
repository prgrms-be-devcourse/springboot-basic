package org.prgrms.kdt.io;

public interface Output {
    void printMessage(String message);
    void printWarnMessage(Exception e);
    void printErrorMessage(Exception e);
}
