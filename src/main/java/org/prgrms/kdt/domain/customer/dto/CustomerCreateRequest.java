package org.prgrms.kdt.domain.customer.dto;

import org.prgrms.kdt.domain.customer.model.Customer;
import org.prgrms.kdt.domain.customer.model.CustomerType;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

public class CustomerCreateRequest {
    @NotNull
    private CustomerType customerType;
    @NotBlank(message = "이름은 필수입력입니다.")
    @Size(max = 10, message = "이름은 10자 이하로 입력해주세요.")
    private String name;
    @NotBlank(message = "이메일은 필수입력입니다.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    @Size(max = 50, message = "이메일은 50자 이하로 입력해주세요.")
    private String email;

    public CustomerCreateRequest() {
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

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Customer toEntity() {
        return new Customer(UUID.randomUUID(),
                name,
                email,
                LocalDateTime.now(),
                LocalDateTime.now());
    }
}
