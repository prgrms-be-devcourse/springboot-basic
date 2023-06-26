package com.dev.bootbasic.view.console;

import com.dev.bootbasic.view.OutputView;
import org.springframework.stereotype.Component;

@Component
public class ConsoleOutputView implements OutputView {
    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }

}
