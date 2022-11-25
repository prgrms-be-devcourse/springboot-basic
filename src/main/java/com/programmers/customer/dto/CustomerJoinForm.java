package com.programmers.customer.dto;

import java.util.regex.Pattern;

import static com.programmers.message.ErrorMessage.ERROR_INPUT_MESSAGE;

public class CustomerJoinForm {
    private String email;
    private String name;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        validateEmail(email);
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        validateName(name);
        this.name = name;
    }

    private void validateName(String name) {
        if (name.isBlank()) {
            throw new RuntimeException(ERROR_INPUT_MESSAGE.getMessage());
        }
    }

    private void validateEmail(String email) {
        String regEx = "\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}\\b";

        if (Pattern.matches(regEx, email)) {
            throw new RuntimeException(ERROR_INPUT_MESSAGE.getMessage());
        }
    }
}
