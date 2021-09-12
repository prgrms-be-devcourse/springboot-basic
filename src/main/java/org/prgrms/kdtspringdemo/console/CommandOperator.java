package org.prgrms.kdtspringdemo.console;

import java.util.List;
import java.util.stream.Stream;

public interface CommandOperator<T> {
    public boolean create(String[] splitList);

    public List<T> getAllitems();
}
