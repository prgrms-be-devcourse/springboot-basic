package com.prgms.management.command.io;

import java.util.List;

public interface Output<T> {
    void printList (List<T> list);
    void printOneItem(T t);
    void printString(String str);
}
