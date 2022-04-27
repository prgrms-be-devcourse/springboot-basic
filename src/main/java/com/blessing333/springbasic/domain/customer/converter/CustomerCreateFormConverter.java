package com.blessing333.springbasic.domain.customer.converter;

import com.blessing333.springbasic.console.customer.ui.CustomerCreateForm;
import com.blessing333.springbasic.domain.customer.model.Customer;

public class CustomerCreateFormConverter {

    public Customer convert(CustomerCreateForm form) {
        CustomerCreateFormValidator.validate(form);
        return Customer.customerBuilder()
                .name(form.getName())
                .email(form.getEmail())
                .build();
    }

    private static class CustomerCreateFormValidator {
        public static void validate(CustomerCreateForm form) {
            String name = form.getName();
            String email = form.getEmail();
            if (name.isBlank() || email.isBlank())
                throw new IllegalArgumentException("이름과 이메일은 반드시 입력해주세요");
        }
    }
}
