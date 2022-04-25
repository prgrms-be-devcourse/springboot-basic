package me.programmers.springboot.basic.springbootbasic.customer.model;

public class CustomerInfo {
    private String name;
    private final String email;

    public CustomerInfo(String name, String email) {
        validateName(name);
        validateName(email);
        this.name = name;
        this.email = email;
    }

    private void validateName(String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("Name should not be blank");
        }
    }

    private void validateEmail(String email) {
        if (email.isBlank()) {
            throw new IllegalArgumentException("Email should not be blank");
        }
    }

    public void changeName(String name) {
        validateName(name);
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}
