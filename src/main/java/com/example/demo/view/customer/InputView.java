package com.example.demo.view.customer;

import com.example.demo.view.validate.CustomerValidator;
import java.util.Scanner;
import java.util.UUID;

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

    public UUID readCustomerId() {
        try {
            return UUID.fromString(sc.nextLine());
        } catch (IllegalArgumentException e) {
            //로거 : 여기서 뭔 일 생겼다 로그.
            //throw : 좀 더 명확한 메세지.
            throw new IllegalArgumentException("명확한 메세지");
        }
    }
}
