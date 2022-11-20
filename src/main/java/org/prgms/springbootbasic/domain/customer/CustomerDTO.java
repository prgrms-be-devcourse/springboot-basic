package org.prgms.springbootbasic.domain.customer;

import java.util.UUID;

public record CustomerDTO(UUID customerId, String email, String name) {
}
