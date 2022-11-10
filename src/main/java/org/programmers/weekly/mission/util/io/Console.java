package org.programmers.weekly.mission.util.io;

import java.io.*;
import java.util.Scanner;


public class Console implements Input, Output {
    private final Scanner scanner = new Scanner(System.in);
    private final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    private final BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

    @Override
    public String getInput() {
        try {
            return bufferedReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
            // error log
        }
    }

    @Override
    public String selectOption(String description) {
        System.out.print(description);
        return scanner.nextLine();
    }

    @Override
    public String selectVoucher(String description) {
        System.out.print(description);
        return scanner.nextLine();
    }

    @Override
    public Long getVoucherDiscount(String input) {
        try {
            return Long.parseLong(input);
        } catch (NumberFormatException e) {
            throw new IllegalAccessError("숫자로 입력하세요.");
        }
    }

    @Override
    public void printMessage(String message) {
        try {
            bufferedWriter.write(message);
            bufferedWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void printObject(Object object) {
        try {
            bufferedWriter.write(object.toString());
            bufferedWriter.write("\n");
            bufferedWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
