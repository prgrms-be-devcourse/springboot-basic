package org.prgrms.kdt.commandapp;


import java.util.*;

public interface CommandOperator<T, V> {

    T create(V v);

    Map<UUID, T> getAll();
}
