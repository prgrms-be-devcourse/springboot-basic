package devcourse.springbootbasic.dto.customer;

import devcourse.springbootbasic.domain.customer.Customer;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class CustomerCreateRequest {

    private final String name;

    public Customer toEntity() {
        return Customer.builder()
                .id(UUID.randomUUID())
                .name(name)
                .isBlacklisted(false)
                .build();
    }
}
