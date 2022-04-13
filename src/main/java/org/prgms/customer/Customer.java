package org.prgms.customer;

import java.util.UUID;

public record Customer(UUID customerId, String name, String email) {
}
