package kdt.vouchermanagement.domain.customer.dto;

import kdt.vouchermanagement.domain.customer.domain.Customer;

import javax.validation.constraints.NotBlank;

public class CustomerCreateRequest {

    @NotBlank(message = "공백이 입력되었습니다.")
    private String name;

    public CustomerCreateRequest() {
    }

    public CustomerCreateRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Customer toDomain() {
        return Customer.of(this);
    }
}
