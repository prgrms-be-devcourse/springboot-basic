package org.prgrms.kdt.common.view.console.io;

import java.util.Scanner;

public class Console implements Input, Output {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String input(String requestMsg) {
        System.out.print(requestMsg);
        return scanner.nextLine();
    }

    @Override
    public void showMessage(String outputMessage) {
        System.out.println(outputMessage);
    }

    public void close(){
        scanner.close();
    }
}
