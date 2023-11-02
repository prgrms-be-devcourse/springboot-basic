package devcourse.springbootbasic.dto.customer;


import devcourse.springbootbasic.domain.customer.Customer;
import lombok.Getter;

@Getter
public class CustomerResponse {

    private final String id;

    public CustomerResponse(Customer customer) {
        this.id = customer.getId().toString();
    }

    @Override
    public String toString() {
        return """
                id = %s
                """.formatted(id);
    }
}
