package org.prgrms.kdt.command;


import java.util.*;

public interface CommandOperator<T, V> {

    T create(V v);

    Map<UUID, T> getAll();
}
