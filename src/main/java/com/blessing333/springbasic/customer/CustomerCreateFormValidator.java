package com.blessing333.springbasic.customer;

import com.blessing333.springbasic.customer.ui.CustomerCreateForm;

public class CustomerCreateFormValidator {
    public void validate(CustomerCreateForm form){
        String name = form.getName();
        String email = form.getEmail();
        if(name.isBlank() || email.isBlank())
            throw new IllegalArgumentException("이름과 이메일은 반드시 입력해주세요");
    }


}
