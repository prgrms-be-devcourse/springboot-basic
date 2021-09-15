package com.programmers.kdtspringorder.io.console;

import com.programmers.kdtspringorder.io.Input;
import com.programmers.kdtspringorder.io.Output;
import com.programmers.kdtspringorder.voucher.domain.Voucher;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Console implements Input, Output {
    private Scanner scanner;

    public Console(){
        scanner = new Scanner(System.in);
    }

    public void printMessage(String message) {
        System.out.println(message);
    }

    public String inputText(){
        return scanner.nextLine();
    }

    public void newLine() {
        System.out.println();
    }
}
