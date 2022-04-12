package org.prgrms.weeklymission.console;

import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;
import java.util.Scanner;

import static org.prgrms.weeklymission.utils.ConsoleMessage.INIT_MESSAGE;

@Component
public class Console implements Input, Output {
    @Override
    public String takeInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    @Override
    public void initMessage() {
        System.out.println(INIT_MESSAGE);
    }

    @Override
    public void errorMessage(Exception e) {
        System.out.println(MessageFormat.format("error: {0}", e.getMessage()));
    }

    @Override
    public <T> void printData(List<T> data) {
        data.forEach(System.out::println);
    }
}
