package com.programmers.console.view;

public interface OutputView {

    <T> void println(T message);

    <T> void print(T message);
}
