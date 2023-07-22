package com.devcourse.voucherapp.view.io;

import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ConsoleOutput implements Output {

    @Override
    public <T> void printWithLineBreak(T data) {
        System.out.println(data);
    }

    @Override
    public <T> void printWithoutLineBreak(T data) {
        System.out.print(data);
    }

    @Override
    public <T> void printElementsInArray(T[] elements) {
        for (T element : elements) {
            printWithLineBreak(element);
        }
    }

    @Override
    public <T> void printElementsInList(List<T> elements) {
        for (T element : elements) {
            printWithLineBreak(element);
        }
    }
}
