package com.example.demo.view.customer;

import com.example.demo.enums.CustomerCommandType;
import com.example.demo.view.validate.CommandValidator;
import com.example.demo.view.validate.CustomerValidator;
import java.util.Scanner;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InputView {

    private static final Logger logger = LoggerFactory.getLogger(InputView.class);
    public static Scanner sc = new Scanner(System.in);


    public CustomerCommandType readCommandOption() {
        String input = sc.nextLine();
        CommandValidator.validateCommandNumberOneToFour(input);
        return CustomerCommandType.from(Integer.parseInt(input));
    }

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
        String input = sc.nextLine();
        try {
            return UUID.fromString(input);
        } catch (IllegalArgumentException e) {
            logger.error("View 에러 : 올바르지 않은 포맷의 UUID를 입력 받았습니다. 입력 값 {} 를 UUID 포맷에 맞추어 고쳐주세요.", input);
            throw new IllegalArgumentException(String.format("올바르지 않은 포맷의 UUID를 입력 받았습니다. 입력 값 %s 를 UUID 포맷에 맞추어 고쳐주세요.", input));
        }
    }
}
