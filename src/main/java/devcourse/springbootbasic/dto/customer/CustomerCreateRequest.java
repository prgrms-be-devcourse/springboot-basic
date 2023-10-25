package devcourse.springbootbasic.dto.customer;

import devcourse.springbootbasic.domain.customer.Customer;
import devcourse.springbootbasic.util.UUIDUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomerCreateRequest {

    private final String name;

    public Customer toEntity() {
        return Customer.builder()
                .id(UUIDUtil.generateRandomUUID())
                .name(name)
                .isBlacklisted(false)
                .build();
    }
}
