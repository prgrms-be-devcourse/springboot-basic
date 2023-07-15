package com.prgrms.presentation.view;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Input {

    private final Scanner sc = new Scanner(System.in);

    public String enterOption() {
        String option = sc.nextLine();
        sc.nextLine();
        return option;
    }

    public int enterID() {
        int option = sc.nextInt();
        sc.nextLine();
        return option;
    }

    public Double enterDiscount() {
        return sc.nextDouble();
    }
}
