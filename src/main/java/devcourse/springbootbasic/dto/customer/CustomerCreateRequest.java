package devcourse.springbootbasic.dto.customer;

import devcourse.springbootbasic.domain.customer.Customer;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class CustomerCreateRequest {

    private final String name;

    public Customer toEntity(UUID uuid) {
        return Customer.builder()
                .id(uuid)
                .name(name)
                .isBlacklisted(false)
                .build();
    }
}
