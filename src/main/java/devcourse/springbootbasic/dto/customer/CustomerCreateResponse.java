package devcourse.springbootbasic.dto.customer;


import devcourse.springbootbasic.domain.customer.Customer;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CustomerCreateResponse {

    private final UUID id;

    public CustomerCreateResponse(Customer customer) {
        this.id = customer.getId();
    }

    @Override
    public String toString() {
        return """
                id = %s
                """.formatted(id);
    }
}
