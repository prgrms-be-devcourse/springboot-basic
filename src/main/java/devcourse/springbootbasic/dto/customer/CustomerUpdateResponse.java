package devcourse.springbootbasic.dto.customer;


import devcourse.springbootbasic.domain.customer.Customer;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CustomerUpdateResponse {

    private final UUID id;

    public CustomerUpdateResponse(Customer customer) {
        this.id = customer.getId();
    }

    @Override
    public String toString() {
        return """
                id = %s
                """.formatted(id);
    }
}
