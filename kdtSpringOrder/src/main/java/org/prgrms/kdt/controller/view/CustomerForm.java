package org.prgrms.kdt.controller.view;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class CustomerForm {

    @NotBlank(message = "회원 이름은 필수 입니다.")
    private String name;

    @Email
    @NotBlank(message = "이메일은 필수 입니다.")
    private String email;

    public String getName() { return name; }

    public String getEmail() { return email; }

    public void setName(String name) { this.name = name; }

    public void setEmail(String email) { this.email = email; }
}
