package com.programmers.springbootbasic.util;

public interface IdProvider<T> {
    T generateId();
}
