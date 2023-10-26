package devcourse.springbootbasic.dto.customer;


import devcourse.springbootbasic.domain.customer.Customer;
import lombok.Getter;

@Getter
public class CustomerFindResponse {

    private final String id;
    private final String name;
    private final String isBlacklisted;

    public CustomerFindResponse(Customer customer) {
        this.id = customer.getId().toString();
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
