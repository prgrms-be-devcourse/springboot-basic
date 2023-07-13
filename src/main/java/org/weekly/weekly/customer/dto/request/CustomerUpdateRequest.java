package org.weekly.weekly.customer.dto.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.weekly.weekly.ui.exception.InputValidator;

public class CustomerUpdateRequest {
    private String email;
    private String newEmail;

    private CustomerUpdateRequest(){}

    private CustomerUpdateRequest(String email, String afterEmail) {
        this.email = email;
        this.newEmail = afterEmail;
    }
    private CustomerUpdateRequest(String email) {
        this.email = email;
    }


    public static CustomerUpdateRequest of(String email) {
        InputValidator.isEmpty(email);
        return new CustomerUpdateRequest(email);
    }


    public static CustomerUpdateRequest of(String email, String afterEmail) {
        InputValidator.isEmpty(email);
        InputValidator.isEmpty(afterEmail);
        return new CustomerUpdateRequest(email, afterEmail);
    }

    public String email() {
        return email;
    }
    public String newEmail() {return newEmail;}

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }
}
