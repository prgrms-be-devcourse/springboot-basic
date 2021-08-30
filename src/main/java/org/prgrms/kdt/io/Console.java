package org.prgrms.kdt.io;

import org.prgrms.kdt.exception.InvalidIOMessageException;

import java.util.Scanner;

public class Console implements Input, Output{
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String readLine() throws InvalidIOMessageException{
        var input = scanner.nextLine();
        if(input == null || input == "")
            throw new InvalidIOMessageException("입력 예외 발생");

        return input;
    }

    @Override
    public void write(String message) throws InvalidIOMessageException {
        if(message == null)
            throw new InvalidIOMessageException("출력문이 null입니다");

        System.out.print(message);
    }
}
