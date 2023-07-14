package com.devcourse.voucherapp.view;

import com.devcourse.voucherapp.entity.Menu;
import com.devcourse.voucherapp.view.io.Input;
import com.devcourse.voucherapp.view.io.Output;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommonView {

    private static final String MENU_TITLE = "\n[할인권 프로그램 v1.0]";
    private static final String INPUT_MESSAGE = "입력 : ";
    private static final String QUIT_MESSAGE = "\n프로그램을 종료합니다.";

    private final Input input;
    private final Output output;

    public String readUserInput() {
        output.printWithoutLineBreak(INPUT_MESSAGE);

        return input.inputWithTrimming();
    }

    public void showHomeMenu() {
        output.printWithLineBreak(MENU_TITLE);
        showElementsInArray(Menu.values());
    }

    public void showExceptionMessage(String message) {
        output.printWithLineBreak(message);
    }

    public void showQuitMessage() {
        output.printWithLineBreak(QUIT_MESSAGE);
    }

    <T> void showElementsInArray(T[] elements) {
        for (T element : elements) {
            output.printWithLineBreak(element);
        }
    }

    <T> void showElementsInList(List<T> elements) {
        for (T element : elements) {
            output.printWithLineBreak(element);
        }
    }
}
