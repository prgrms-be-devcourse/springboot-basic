package com.prgrms.view.view;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Input {

    private final Scanner sc = new Scanner(System.in);

    public String enterOption() {
        return sc.next();
    }

    public Double enterDiscount() {
        return sc.nextDouble();
    }
}
