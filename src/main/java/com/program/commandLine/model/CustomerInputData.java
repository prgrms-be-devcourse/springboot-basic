package com.program.commandLine.model;

import com.program.commandLine.model.customer.CustomerType;
import org.springframework.util.Assert;

import java.util.UUID;
import java.util.regex.Pattern;

public class CustomerInputData {
    private final CustomerType customerType;
    private final String name;
    private final String email;
    private final UUID customerId;

    public CustomerInputData(String customerType, String name, String email) {
        Assert.isTrue(stringValidation(name),"! 이름을 입력하세요.");
        Assert.isTrue(stringValidation(email),"! 이메일을 입력하세요.");
        Assert.isTrue(checkEmail(email),"! 올바른 이메일이 아닙니다.");
        this.customerType = CustomerType.getType(customerType);
        this.name = name;
        this.email = email;
        this.customerId = UUID.randomUUID();
    }

    private boolean stringValidation(String input){
        return !input.isBlank();
    }
    private boolean checkEmail(String email){
        return Pattern.matches("\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}\\b",email);
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public UUID getCustomerId() {
        return customerId;
    }
}
