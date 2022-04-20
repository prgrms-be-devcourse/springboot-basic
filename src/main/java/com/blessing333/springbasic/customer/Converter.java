package com.blessing333.springbasic.customer;

import com.blessing333.springbasic.customer.domain.Customer;
import com.blessing333.springbasic.customer.ui.CustomerCreateForm;

public class Converter {
    private final CustomerCreateFormValidator validator = new CustomerCreateFormValidator();

    public Customer convert(CustomerCreateForm form) {
        validator.validate(form);
        return Customer.customerBuilder()
                .name(form.getName())
                .email(form.getEmail())
                .build();
    }
}
