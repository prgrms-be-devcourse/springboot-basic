package org.prgrms.kdtspringdemo.console;

import java.util.stream.Stream;

public interface CommandOperator<T> {
    public T create(String[] splitList);

    public Stream<T> getAllitems();
}
