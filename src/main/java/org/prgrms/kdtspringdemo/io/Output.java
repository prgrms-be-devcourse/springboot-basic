package org.prgrms.kdtspringdemo.io;

import java.util.stream.Stream;

public interface Output {
    void printCommandList();

    void printStartAppInfo();

    void printCommandError(String command);

    void printCreateTypes();

    <T> void printStream(Stream<T> stream);
}
