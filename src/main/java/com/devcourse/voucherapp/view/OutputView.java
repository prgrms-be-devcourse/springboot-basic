package com.devcourse.voucherapp.view;

public interface OutputView {

    <T> void printWithLineBreak(T data);

    <T> void printWithoutLineBreak(T data);
}
