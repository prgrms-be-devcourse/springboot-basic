package devcourse.springbootbasic.dto.customer;


import devcourse.springbootbasic.domain.customer.Customer;

import java.util.UUID;

public class CustomerFindResponse {

    private final UUID id;
    private final String name;
    private final String isBlacklisted;

    public CustomerFindResponse(Customer customer) {
        this.id = customer.getId();
        this.name = customer.getName();
        this.isBlacklisted = customer.isBlacklisted() ? "Yes" : "No";
    }

    @Override
    public String toString() {
        return """
                id = %s
                name = %s
                isBlacklisted = %s
                """.formatted(id, name, isBlacklisted);
    }
}
