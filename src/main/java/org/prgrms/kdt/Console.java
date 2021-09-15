package org.prgrms.kdt;

import org.prgrms.kdt.IO.Input;
import org.prgrms.kdt.IO.Output;

import java.util.List;
import java.util.Scanner;

public class Console implements Input, Output {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String input(String guide) {
        System.out.println(guide);
        return scanner.nextLine();
    }

    @Override
    public void print(String str) {
        System.out.println(str);
    }

    @Override
    public void print(List<?> list) {
        if (list.isEmpty()) {
            System.out.println("내용이 없습니다.");
            return;
        }
        list.forEach(System.out::println);
    }

}
