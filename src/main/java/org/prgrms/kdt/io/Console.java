package org.prgrms.kdt.io;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Console {

    Scanner sc = new Scanner(System.in);

    public String read() {
        return sc.nextLine();
    }

    public void write(String text) {
        System.out.println(text);
    }
}
