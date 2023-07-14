package org.weekly.weekly.customer.dto.request;

import org.weekly.weekly.ui.exception.InputValidator;

public class CustomerUpdateRequest {
    private String email;
    private String newEmail;

    private CustomerUpdateRequest() {
    }

    public CustomerUpdateRequest(String email, String afterEmail) {
        InputValidator.isEmpty(email);
        InputValidator.isEmpty(afterEmail);
        this.email = email;
        this.newEmail = afterEmail;
    }

    public CustomerUpdateRequest(String email) {
        InputValidator.isEmpty(email);
        this.email = email;
    }

    public String email() {
        return email;
    }

    public String newEmail() {
        return newEmail;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }
}
