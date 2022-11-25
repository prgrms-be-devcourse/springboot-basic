package org.prgrms.springorder.domain.customer.view;

public class CustomerCreateRequest {

    private final String name;

    private final String email;

    public CustomerCreateRequest(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
