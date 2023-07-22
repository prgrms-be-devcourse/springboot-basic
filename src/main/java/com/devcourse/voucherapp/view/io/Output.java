package com.devcourse.voucherapp.view.io;

import java.util.List;

public interface Output {

    <T> void printWithLineBreak(T data);

    <T> void printWithoutLineBreak(T data);

    <T> void printElementsInArray(T[] elements);

    <T> void printElementsInList(List<T> elements);
}
