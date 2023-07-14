package com.example.demo.view.customer;

import com.example.demo.view.validate.CustomerValidator;
import java.util.Scanner;

public class InputView {

    public static Scanner sc = new Scanner(System.in);

    public String readCustomerName() {
        String input = sc.nextLine();
        CustomerValidator.validateName(input);
        return input;
    }

    public int readCustomerAge() {
        String input = sc.nextLine();
        CustomerValidator.validateAge(input);
        return Integer.parseInt(input);
    }
}
