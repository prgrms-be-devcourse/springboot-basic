package org.prgrms.springorder.domain.customer.api.response;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.prgrms.springorder.domain.customer.model.CustomerStatus;

@Getter
@AllArgsConstructor
public class CustomerResponse {

    private final UUID customerId;

    private final String name;

    private final String email;

    private final LocalDateTime lastLoginAt;

    private final LocalDateTime createdAt;

    private final CustomerStatus customerStatus;

}
