package com.devcourse.voucherapp.view.io;

public interface Output {

    <T> void printWithLineBreak(T data);

    <T> void printWithoutLineBreak(T data);
}
