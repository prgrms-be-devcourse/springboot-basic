package org.prgrms.kdtspringdemo.view.console.output;

public interface Output {
    <T> void write(T value);

    <T> void writeFormat(String format, T... value);
}
